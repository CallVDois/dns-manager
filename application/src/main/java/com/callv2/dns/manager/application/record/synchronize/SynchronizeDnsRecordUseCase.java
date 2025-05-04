package com.callv2.dns.manager.application.record.synchronize;

import com.callv2.dns.manager.application.UnitUseCase;
import com.callv2.dns.manager.domain.common.event.DomainErrorOccurredEvent;
import com.callv2.dns.manager.domain.event.EventDispatcher;
import com.callv2.dns.manager.domain.exception.DomainException;
import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordGateway;
import com.callv2.dns.manager.domain.record.DnsRecordID;
import com.callv2.dns.manager.domain.record.DnsRecordName;
import com.callv2.dns.manager.domain.record.DnsRecordType;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;
import com.callv2.dns.manager.domain.validation.ValidationError;
import com.callv2.dns.manager.domain.validation.handler.Notification;

public class SynchronizeDnsRecordUseCase extends UnitUseCase<SynchronizeDnsRecordInput> {

    private final IpGateway ipGateway;
    private final DnsRecordGateway dnsRecordGateway;

    private final EventDispatcher eventDispatcher;

    public SynchronizeDnsRecordUseCase(
            final IpGateway ipGateway,
            final DnsRecordGateway dnsRecordGateway,
            final EventDispatcher eventDispatcher) {
        this.ipGateway = ipGateway;
        this.dnsRecordGateway = dnsRecordGateway;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public void execute(final SynchronizeDnsRecordInput input) {

        final DnsRecordType dnsRecordType = input.type();
        final DnsRecordName dnsRecordName = DnsRecordName.of(input.dns());
        final DnsRecordID dnsRecordID = DnsRecordID.from(dnsRecordName, dnsRecordType);
        final IpType ipType = input.type().getIpType();

        final Notification notification = Notification.create();

        final Ip currentPublicIP = notification.validate(() -> this.ipGateway.findPublicIp(ipType));

        if (notification.hasError()) {
            eventDispatcher.notify(DomainErrorOccurredEvent.create("dns.manager",
                    DomainErrorOccurredEvent.Data.of(DomainException
                            .with("An error occurred on findPublicIp",
                                    notification.getErrors().stream().map(ValidationError::toDomain).toList()))));
            return;
        }

        final DnsRecord dnsRecord = this.dnsRecordGateway
                .findById(dnsRecordID)
                .orElse(DnsRecord.create(dnsRecordName, input.type()));

        if (dnsRecord.getIp().equals(currentPublicIP))
            return;

        notification.validate(() -> dnsRecord.changeIp(currentPublicIP));

        if (notification.hasError()) {
            eventDispatcher.notify(dnsRecord);
            return;
        }

        this.dnsRecordGateway.update(dnsRecord);
        eventDispatcher.notify(dnsRecord);
    }

}
