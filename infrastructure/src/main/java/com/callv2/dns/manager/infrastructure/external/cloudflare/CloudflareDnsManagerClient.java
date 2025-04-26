package com.callv2.dns.manager.infrastructure.external.cloudflare;

import java.util.Map;

import org.springframework.web.reactive.function.client.WebClient;

import com.callv2.dns.manager.infrastructure.external.cloudflare.model.ListDNSResponse;

public class CloudflareDnsManagerClient {

    private final String zoneId;
    private final String token;

    public CloudflareDnsManagerClient(
            final String zoneId,
            final String token) {
        this.zoneId = zoneId;
        this.token = token;
    }

    public void updateDns(final String dns, final String dnsType, final String ip) {
        listDNS()
                .result()
                .stream()
                .filter(record -> record.name().equals(dns))
                .filter(record -> record.type().equals(dnsType))
                .forEach(record -> {
                    WebClient.create("https://api.cloudflare.com")
                            .patch()
                            .uri("/client/v4/zones/{zoneId}/dns_records/{recordId}", this.zoneId, record.id())
                            .header("Authorization", "Bearer " + this.token)
                            .bodyValue(Map.of("content", ip))
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
