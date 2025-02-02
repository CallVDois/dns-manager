package com.callv2.drive.dns.manager.ipify;

import org.springframework.web.reactive.function.client.WebClient;

import com.callv2.dns.manager.domain.ip.IP;
import com.callv2.dns.manager.domain.ip.PublicIPRetriever;
import com.callv2.dns.manager.domain.ip.Type;
import com.callv2.drive.dns.manager.ipify.model.IPResponse;

public class IpifyPublicIPRetriever implements PublicIPRetriever {

    @Override
    public IP retrieve(Type type) {

        if (type == Type.IPV6)
            throw new UnsupportedOperationException("IPV6 is not supported yeat");

        return present(retrievePublicIPV4(), type);
    }

    private static IP present(final IPResponse ipResponse, final Type type) {
        return new IP(ipResponse.ip(), type);
    }

    private IPResponse retrievePublicIPV4() {
        return WebClient.create("https://api.ipify.org/")
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("format", "json").build())
                .retrieve()
                .bodyToMono(IPResponse.class)
                .block();

    }

}
