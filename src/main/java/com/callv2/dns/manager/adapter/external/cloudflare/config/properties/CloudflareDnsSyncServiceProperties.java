package com.callv2.dns.manager.adapter.external.cloudflare.config.properties;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CloudflareDnsSyncServiceProperties {

    private CloudflareDnsSyncServiceProperties.Api api;
    private List<CloudflareDnsSyncServiceProperties.Service> services;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Api {
        private String token;
        private String zoneId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Service {

        private String dnsName;
        private String ipType;
    }

}
