package com.callv2.dns.manager.infrastructure.api.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordUseCase;
import com.callv2.dns.manager.infrastructure.api.DnsAPI;
import com.callv2.dns.manager.infrastructure.record.adapter.DnsRecordAdapter;
import com.callv2.dns.manager.infrastructure.record.model.UpdateDnsRecordRequest;

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
