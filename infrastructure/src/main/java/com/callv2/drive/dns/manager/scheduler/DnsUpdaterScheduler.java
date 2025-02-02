package com.callv2.drive.dns.manager.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.callv2.dns.manager.domain.DnsSyncService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DnsUpdaterScheduler {

    private final List<DnsSyncService> dnsSyncServices;

    public DnsUpdaterScheduler(final List<DnsSyncService> dnsSyncServices) {
        this.dnsSyncServices = dnsSyncServices;
    }

    @Scheduled(fixedDelay = 600000)
    public void updateDns() {

        try {
            for (DnsSyncService syncService : dnsSyncServices) {
                log.info("Updating DNS record for {}", syncService.getDns().value());
                syncService.sync();
                log.info("Updating DNS record for {} updated IP : {}",
                        syncService.getDns().value(),
                        syncService.getLastPublicIP().value());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
