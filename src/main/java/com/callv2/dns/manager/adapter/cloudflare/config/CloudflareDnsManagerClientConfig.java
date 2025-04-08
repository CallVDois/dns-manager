package com.callv2.dns.manager.adapter.cloudflare.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.callv2.dns.manager.adapter.cloudflare.CloudflareDnsManagerClient;

@Configuration
@Profile({ "cloudflare" })
public class CloudflareDnsManagerClientConfig {

    @Bean
    CloudflareDnsManagerClient cloudflareDnsManagerClient(
            @Value("${cloudflare.api.zoneId}") final String zoneId,
            @Value("${cloudflare.api.token}") final String token) {
        return new CloudflareDnsManagerClient(zoneId, token);
    }

    // @Bean
    // @ConfigurationProperties("cloudflare")
    // CloudflareDnsSyncServiceProperties googleCloudProperties() {
    // return new CloudflareDnsSyncServiceProperties();
    // }

    // @Bean
    // List<DnsSyncService> cloudflareDnsRecordSyncService(
    // final CloudflareDnsSyncServiceProperties properties,
    // final PublicIPRetriever publicIPRetriever) {

    // final String zoneId = properties.getApi().getZoneId();
    // final String token = properties.getApi().getToken();

    // return properties.getServices().stream()
    // .map(it -> map(it, publicIPRetriever, zoneId, token))
    // .toList();
    // }

    // private static DnsSyncService map(
    // final CloudflareDnsSyncServiceProperties.Service service,
    // final PublicIPRetriever publicIPRetriever,
    // final String zoneId,
    // final String token) {
    // final DNS dns = new DNS(service.getDnsName(),
    // Type.valueOf(service.getIpType()));
    // return new CloudflareDnsSyncService(dns, publicIPRetriever,
    // dns.type().getIpType(), zoneId, token);
    // }

}
