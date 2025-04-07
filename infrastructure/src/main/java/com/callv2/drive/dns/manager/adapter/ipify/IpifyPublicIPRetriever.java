package com.callv2.drive.dns.manager.adapter.ipify;

import org.springframework.web.reactive.function.client.WebClient;

import com.callv2.drive.dns.manager.adapter.ipify.model.IPResponse;

public class IpifyPublicIPRetriever {

    public IPResponse retrievePublicIPV4() {
        return WebClient.create("https://api.ipify.org/")
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("format", "json").build())
                .retrieve()
                .bodyToMono(IPResponse.class)
                .block();
    }

    public IPResponse retrievePublicIPV6() {
        return WebClient.create("https://api64.ipify.org/")
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("format", "json").build())
                .retrieve()
                .bodyToMono(IPResponse.class)
                .block();
    }

}
