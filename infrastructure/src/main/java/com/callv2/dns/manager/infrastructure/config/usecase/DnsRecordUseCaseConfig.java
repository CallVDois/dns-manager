package com.callv2.dns.manager.infrastructure.config.usecase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordUseCase;
import com.callv2.dns.manager.domain.event.EventDispatcher;
import com.callv2.dns.manager.domain.record.DnsRecordGateway;
import com.callv2.dns.manager.domain.record.IpGateway;

@Configuration
public class DnsRecordUseCaseConfig {

    @Bean
    SynchronizeDnsRecordUseCase synchronizeDnsRecordUseCase(
            final IpGateway ipGateway,
            final DnsRecordGateway dnsRecordGateway,
            final EventDispatcher eventDispatcher) {
        return new SynchronizeDnsRecordUseCase(ipGateway, dnsRecordGateway, eventDispatcher);
    }
}
