package com.callv2.drive.dns.manager.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.callv2.dns.manager.domain.DnsSyncService;
import com.callv2.drive.dns.manager.api.DnsAPI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DnsController implements DnsAPI {

    private final List<DnsSyncService> dnsSyncServices;

    public DnsController(final List<DnsSyncService> dnsSyncServices) {
        this.dnsSyncServices = dnsSyncServices;
    }

    @Override
    public void forceUpdate() {
        for (DnsSyncService syncService : dnsSyncServices) {
            log.info("Updating DNS record for {}", syncService.getDns().value());
            syncService.sync();
            log.info("Updating DNS record for {} updated IP : {}",
                    syncService.getDns().value(),
                    syncService.getLastPublicIP().value());
        }
    }

}
