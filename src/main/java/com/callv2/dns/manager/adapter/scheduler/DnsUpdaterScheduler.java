package com.callv2.dns.manager.adapter.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordInput;
import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordUseCase;
import com.callv2.dns.manager.domain.record.DnsRecordType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DnsUpdaterScheduler {

    private final List<String> ipv4DnsList;
    private final List<String> ipv6DnsList;

    private final SynchronizeDnsRecordUseCase synchronizeDNSUseCase;

    public DnsUpdaterScheduler(
            final List<String> ipv4DnsList,
            final List<String> ipv6DnsList,
            final SynchronizeDnsRecordUseCase synchronizeDNSUseCase) {
        this.ipv4DnsList = ipv4DnsList;
        this.ipv6DnsList = ipv6DnsList;
        this.synchronizeDNSUseCase = synchronizeDNSUseCase;
    }

    @Scheduled(fixedDelayString = "${dns.update.delay}")
    public void updateDns() {

        for (String dns : ipv4DnsList)
            synchronizeDNSUseCase.execute(new SynchronizeDnsRecordInput(dns, DnsRecordType.A));

        for (String dns : ipv6DnsList)
            synchronizeDNSUseCase.execute(new SynchronizeDnsRecordInput(dns, DnsRecordType.AAAA));

    }
}
