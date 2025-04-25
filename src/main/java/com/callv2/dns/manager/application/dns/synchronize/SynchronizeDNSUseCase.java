package com.callv2.dns.manager.application.dns.synchronize;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.application.UnitUseCase;
import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.dns.DNSGateway;
import com.callv2.dns.manager.domain.record.Ip;
import com.callv2.dns.manager.domain.record.IpGateway;
import com.callv2.dns.manager.domain.record.IpType;

@Component
public class SynchronizeDNSUseCase extends UnitUseCase<SynchronizeDNSInput> {

    private final IpGateway ipGateway;
    private final DNSGateway dnsGateway;

    public SynchronizeDNSUseCase(
            final IpGateway ipGateway,
            final DNSGateway dnsGateway) {
        this.ipGateway = ipGateway;
        this.dnsGateway = dnsGateway;
    }

    @Override
    public void execute(final SynchronizeDNSInput input) {

        final DNS dns = new DNS(input.dns(), input.type());

        final IpType ipType = input.type().getIpType();
        final Ip currentPublicIP = this.ipGateway.discoveryPublicIp(ipType);
        final Ip currentIP = this.ipGateway.currentPublicIp(ipType).orElse(null);

        if (currentPublicIP.equals(currentIP))
            return;

        this.ipGateway.updateCurrentPublicIp(currentPublicIP);
        this.dnsGateway.updateIP(dns, currentPublicIP);
    }

}
