package com.callv2.dns.manager.application.record.synchronize;

import com.callv2.dns.manager.application.UnitUseCase;
import com.callv2.dns.manager.domain.event.EventDispatcher;
import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordGateway;
import com.callv2.dns.manager.domain.record.DnsRecordID;
import com.callv2.dns.manager.domain.record.DnsRecordName;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;

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

        final IpType ipType = input.type().getIpType();
        final Ip currentPublicIP = this.ipGateway.findPublicIp(ipType);

        final DnsRecord dnsRecord = this.dnsRecordGateway
                .findById(DnsRecordID.from(DnsRecordName.of(input.dns()), input.type()))
                .orElse(DnsRecord.create(
                        DnsRecordID.from(DnsRecordName.of(input.dns()), input.type()),
                        DnsRecordName.of(input.dns()),
                        input.type(),
                        localHostIp(input.type().getIpType())));

        if (dnsRecord.getIp().equals(currentPublicIP))
            return;

        try {
            this.dnsRecordGateway.update(dnsRecord.changeIp(currentPublicIP));
        } catch (Exception e) {
            throw e;
        } finally {
            eventDispatcher.notify(dnsRecord);
        }

    }

    private Ip localHostIp(final IpType ipType) {
        return IpType.IPV4.equals(ipType)
                ? Ip.fromIpv4("127.0.0.1")
                : Ip.fromIpv6("::1");
    }

}
