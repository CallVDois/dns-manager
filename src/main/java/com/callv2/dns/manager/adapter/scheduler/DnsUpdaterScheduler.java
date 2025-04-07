package com.callv2.dns.manager.adapter.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.callv2.dns.manager.application.dns.synchronize.SynchronizeDNSInput;
import com.callv2.dns.manager.application.dns.synchronize.SynchronizeDNSUseCase;
import com.callv2.dns.manager.domain.dns.DNS;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DnsUpdaterScheduler {

    private final List<DNS> dnsList;
    private final SynchronizeDNSUseCase synchronizeDNSUseCase;

    public DnsUpdaterScheduler(
            final List<DNS> dnsList,
            final SynchronizeDNSUseCase synchronizeDNSUseCase) {
        this.dnsList = dnsList;
        this.synchronizeDNSUseCase = synchronizeDNSUseCase;
    }

    @Scheduled(fixedDelay = 600000)
    public void updateDns() {
        for (DNS dns : dnsList)
            synchronizeDNSUseCase.execute(new SynchronizeDNSInput(dns.value(), dns.type()));
    }
}
