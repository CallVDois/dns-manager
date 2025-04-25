package com.callv2.dns.manager.adapter.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.callv2.dns.manager.adapter.api.DnsAPI;
import com.callv2.dns.manager.adapter.record.adapter.DnsRecordAdapter;
import com.callv2.dns.manager.adapter.record.model.UpdateDnsRecordRequest;
import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordUseCase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DnsController implements DnsAPI {

    private final SynchronizeDnsRecordUseCase synchronizeDNSUseCase;

    public DnsController(final SynchronizeDnsRecordUseCase synchronizeDNSUseCase) {
        this.synchronizeDNSUseCase = synchronizeDNSUseCase;
    }

    @Override
    public void forceUpdate(final List<UpdateDnsRecordRequest> dnsList) {
        for (UpdateDnsRecordRequest updateDNSRequest : dnsList)
            synchronizeDNSUseCase.execute(DnsRecordAdapter.adapt(updateDNSRequest));
    }

}
