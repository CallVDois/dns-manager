package com.callv2.dns.manager.adapter.external.ipify;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.callv2.dns.manager.adapter.external.ipify.model.IPResponse;

@Component
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
