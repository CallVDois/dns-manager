package com.callv2.dns.manager.adapter.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.callv2.dns.manager.domain.dns.DNS;

@Configuration
public class DNSConfig {

    @Bean
    List<DNS> dnsList(
            @Value("${a-dns-list}") String adDnsList,
            @Value("${aaaa-dns-list}") String aaaadDnsList) {

        final ArrayList<DNS> dnsList = new ArrayList<>();
        if (adDnsList != null)
            dnsList
                    .addAll(Arrays.asList(Objects.requireNonNull(adDnsList)
                            .trim()
                            .split(","))
                            .stream()
                            .filter(dns -> dns != null)
                            .filter(dns -> !dns.isBlank())
                            .map(dns -> new DNS(dns, DNS.Type.A))
                            .toList());
        if (aaaadDnsList != null)
            dnsList
                    .addAll(Arrays.asList(Objects.requireNonNull(aaaadDnsList)
                            .trim()
                            .split(","))
                            .stream()
                            .filter(dns -> dns != null)
                            .filter(dns -> !dns.isBlank())
                            .map(dns -> new DNS(dns, DNS.Type.AAAA))
                            .toList());

        return List.copyOf(dnsList);
    }

}
