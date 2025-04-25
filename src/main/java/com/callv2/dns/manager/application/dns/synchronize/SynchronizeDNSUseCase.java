package com.callv2.dns.manager.application.dns.synchronize;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.application.UnitUseCase;
import com.callv2.dns.manager.domain.event.Event;
import com.callv2.dns.manager.domain.record.DnsRecord;
import com.callv2.dns.manager.domain.record.DnsRecordGateway;
import com.callv2.dns.manager.domain.record.DnsRecordID;
import com.callv2.dns.manager.domain.record.DnsRecordName;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;

@Component
public class SynchronizeDNSUseCase extends UnitUseCase<SynchronizeDNSInput> {

    private final IpGateway ipGateway;
    private final DnsRecordGateway dnsRecordGateway;

    public SynchronizeDNSUseCase(
            final IpGateway ipGateway,
            final DnsRecordGateway dnsRecordGateway) {
        this.ipGateway = ipGateway;
        this.dnsRecordGateway = dnsRecordGateway;
    }

    @Override
    public void execute(final SynchronizeDNSInput input) {

        final IpType ipType = input.type().getIpType();
        final Ip currentPublicIP = this.ipGateway.findPublicIp(ipType);

        final DnsRecord dnsRecord = this.dnsRecordGateway
                .findById(DnsRecordID.from(DnsRecordName.of(input.dns()), input.type()))
                .orElseThrow(() -> new IllegalArgumentException("DnsRecord not found"));

        if (dnsRecord.getIp().equals(currentPublicIP))
            return;

        this.dnsRecordGateway.update(dnsRecord.changeIp(currentPublicIP));

        Optional<Event<?>> event = dnsRecord.dequeueEvent();
        while (event.isPresent()) {
            event.get(); // Dispatch event
            event = dnsRecord.dequeueEvent();
        }

    }

}
