package com.callv2.drive.dns.manager.cloudflare.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.callv2.dns.manager.domain.DnsSyncService;
import com.callv2.dns.manager.domain.dns.DNS;
import com.callv2.dns.manager.domain.dns.Type;
import com.callv2.dns.manager.domain.ip.PublicIPRetriever;
import com.callv2.drive.dns.manager.cloudflare.CloudflareDnsSyncService;
import com.callv2.drive.dns.manager.cloudflare.config.properties.CloudflareDnsSyncServiceProperties;

@Configuration
@Profile({ "cloudflare" })
public class CloudflareDnsRecordSyncServiceConfig {

    @Bean
    @ConfigurationProperties("cloudflare")
    CloudflareDnsSyncServiceProperties googleCloudProperties() {
        return new CloudflareDnsSyncServiceProperties();
    }

    @Bean
    List<DnsSyncService> cloudflareDnsRecordSyncService(
            final CloudflareDnsSyncServiceProperties properties,
            final PublicIPRetriever publicIPRetriever) {

        final String zoneId = properties.getApi().getZoneId();
        final String token = properties.getApi().getToken();

        return properties.getServices().stream()
                .map(it -> map(it, publicIPRetriever, zoneId, token))
                .toList();
    }

    private static DnsSyncService map(
            final CloudflareDnsSyncServiceProperties.Service service,
            final PublicIPRetriever publicIPRetriever,
            final String zoneId,
            final String token) {
        final DNS dns = new DNS(service.getDnsName(), Type.valueOf(service.getIpType()));
        return new CloudflareDnsSyncService(dns, publicIPRetriever, dns.type().getIpType(), zoneId, token);
    }

}
