package com.callv2.drive.dns.manager.adapter.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.callv2.drive.dns.manager.domain.dns.DNS;

@Configuration
public class DNSConfig {

    @Bean
    List<DNS> dnsList(
            @Value("${a-dns-list}") String adDnsList,
            @Value("${a-dns-list}") String aaaadDnsList) {

        final ArrayList<DNS> dnsList = new ArrayList<>();
        if (adDnsList != null)
            dnsList
                    .addAll(Arrays.asList(Objects.requireNonNull(adDnsList)
                            .split(","))
                            .stream()
                            .map(dns -> new DNS(dns, DNS.Type.A))
                            .toList());
        if (aaaadDnsList != null)
            dnsList
                    .addAll(Arrays.asList(Objects.requireNonNull(aaaadDnsList)
                            .split(","))
                            .stream()
                            .map(dns -> new DNS(dns, DNS.Type.AAAA))
                            .toList());

        return List.copyOf(dnsList);
    }

}
