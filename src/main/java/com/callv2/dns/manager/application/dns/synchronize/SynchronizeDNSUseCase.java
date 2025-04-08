package com.callv2.dns.manager.application.dns.synchronize;

import org.springframework.stereotype.Component;

import com.callv2.dns.manager.application.UnitUseCase;
import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.dns.DNSGateway;
import com.callv2.dns.manager.domain.ip.IP;
import com.callv2.dns.manager.domain.ip.IPGateway;

@Component
public class SynchronizeDNSUseCase extends UnitUseCase<SynchronizeDNSInput> {

    private final IPGateway ipGateway;
    private final DNSGateway dnsGateway;

    public SynchronizeDNSUseCase(
            final IPGateway ipGateway,
            final DNSGateway dnsGateway) {
        this.ipGateway = ipGateway;
        this.dnsGateway = dnsGateway;
    }

    @Override
    public void execute(final SynchronizeDNSInput input) {

        final DNS dns = new DNS(input.dns(), input.type());

        final IP.Type ipType = input.type().getIpType();
        final IP currentPublicIP = this.ipGateway.discoveryPublicIP(ipType);
        final IP currentIP = this.ipGateway.currentPublicIP(ipType).orElse(null);

        if (currentPublicIP.equals(currentIP))
            return;

        this.ipGateway.updateCurrentIP(currentPublicIP);
        this.dnsGateway.updateIP(dns, currentPublicIP);
    }

}
