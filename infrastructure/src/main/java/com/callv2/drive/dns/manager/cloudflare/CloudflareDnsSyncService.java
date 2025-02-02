package com.callv2.drive.dns.manager.cloudflare;

import java.util.Map;

import org.springframework.web.reactive.function.client.WebClient;

import com.callv2.dns.manager.domain.DnsSyncService;
import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.ip.IP;
import com.callv2.dns.manager.domain.ip.PublicIPRetriever;
import com.callv2.dns.manager.domain.ip.Type;
import com.callv2.drive.dns.manager.cloudflare.model.ListDNSResponse;

public class CloudflareDnsSyncService extends DnsSyncService {

    private final String zoneId;
    private final String token;

    public CloudflareDnsSyncService(
            final DNS dns,
            final PublicIPRetriever publicIPRetriever,
            final Type ipType,
            final String zoneId,
            final String token) {
        super(dns, publicIPRetriever, ipType);
        this.zoneId = zoneId;
        this.token = token;
    }

    @Override
    protected void updateDns(DNS dns, IP publicIP) {
        listDNS()
                .result()
                .stream()
                .filter(it -> it.name().equals(dns.value()))
                .filter(it -> it.type().equals(dns.type().getValue()))
                .forEach(it -> {
                    WebClient.create("https://api.cloudflare.com")
                            .patch()
                            .uri("/client/v4/zones/{zoneId}/dns_records/{recordId}", this.zoneId, it.id())
                            .header("Authorization", "Bearer " + this.token)
                            .bodyValue(Map.of("content", publicIP.value()))
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();
                });
    }

    private ListDNSResponse listDNS() {
        return WebClient.create("https://api.cloudflare.com")
                .get()
                .uri("/client/v4/zones/{zoneId}/dns_records", this.zoneId)
                .header("Authorization", "Bearer " + this.token)
                .retrieve()
                .bodyToMono(ListDNSResponse.class)
                .block();
    }

}
