package com.callv2.drive.dns.manager.application.dns.synchronize;

import com.callv2.drive.dns.manager.application.UnitUseCase;
import com.callv2.drive.dns.manager.domain.dns.DNSGateway;
import com.callv2.drive.dns.manager.domain.ip.IP;
import com.callv2.drive.dns.manager.domain.ip.IPGateway;

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

        final IP.Type ipType = input.type().getIpType();

        final IP currentPublicIP = this.ipGateway.discoveryPublicIP(ipType);
        final IP currentIP = this.ipGateway.currentPublicIP(ipType).orElse(null);

        if (currentPublicIP.equals(currentIP))
            return;

        dnsGateway
                .listAll()
                .stream()
                .filter(dns -> input.dns().equals(dns.value()))
                .forEach(dns -> this.dnsGateway.updateIP(dns, currentPublicIP));
    }

}
