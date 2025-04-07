package com.callv2.dns.manager.adapter.dns;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.callv2.dns.manager.adapter.cloudflare.CloudflareDnsManagerClient;
import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.dns.DNSGateway;
import com.callv2.dns.manager.domain.ip.IP;

@Component
@Profile({ "cloudflare" })
public class CloudflareDNSGateway implements DNSGateway {

    private final CloudflareDnsManagerClient cloudflareDnsManagerClient;

    public CloudflareDNSGateway(final CloudflareDnsManagerClient cloudflareDnsManagerClient) {
        this.cloudflareDnsManagerClient = cloudflareDnsManagerClient;
    }

    @Override
    public void updateIP(DNS dns, IP ip) {
        this.cloudflareDnsManagerClient.updateDns(dns.value(), dns.type().getValue(), ip.value());
    }

}
