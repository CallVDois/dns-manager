package com.callv2.drive.dns.manager.adapter.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.callv2.drive.dns.manager.adapter.api.DnsAPI;
import com.callv2.drive.dns.manager.adapter.dns.adapter.DNSAdapter;
import com.callv2.drive.dns.manager.adapter.dns.model.UpdateDNSRequest;
import com.callv2.drive.dns.manager.application.dns.synchronize.SynchronizeDNSUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DnsController implements DnsAPI {

    private final SynchronizeDNSUseCase synchronizeDNSUseCase;

    public DnsController(final SynchronizeDNSUseCase synchronizeDNSUseCase) {
        this.synchronizeDNSUseCase = synchronizeDNSUseCase;
    }

    @Override
    public void forceUpdate(final List<UpdateDNSRequest> dnsList) {
        for (UpdateDNSRequest updateDNSRequest : dnsList)
            synchronizeDNSUseCase.execute(DNSAdapter.adapt(updateDNSRequest));
    }

}
