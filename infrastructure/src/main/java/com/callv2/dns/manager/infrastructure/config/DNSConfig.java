package com.callv2.dns.manager.infrastructure.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DNSConfig {

    @Bean("ipv4DnsList")
    List<String> ipv4DnsList(@Value("${a-dns-list}") String adDnsList) {

        final ArrayList<String> dnsList = new ArrayList<>();

        if (adDnsList != null)
            dnsList
                    .addAll(Arrays.asList(Objects.requireNonNull(adDnsList)
                            .trim()
                            .split(","))
                            .stream()
                            .filter(dns -> dns != null)
                            .filter(dns -> !dns.isBlank())
                            .toList());

        return List.copyOf(dnsList);
    }

    @Bean("ipv6DnsList")
    List<String> ipv6DnsList(@Value("${aaaa-dns-list}") String aaaadDnsList) {

        final ArrayList<String> dnsList = new ArrayList<>();

        if (aaaadDnsList != null)
            dnsList
                    .addAll(Arrays.asList(Objects.requireNonNull(aaaadDnsList)
                            .trim()
                            .split(","))
                            .stream()
                            .filter(dns -> dns != null)
                            .filter(dns -> !dns.isBlank())
                            .toList());

        return List.copyOf(dnsList);
    }

}
