package com.callv2.dns.manager.infrastructure.record.adapter;

import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordInput;
import com.callv2.dns.manager.infrastructure.record.model.UpdateDnsRecordRequest;

public interface DnsRecordAdapter {

    static SynchronizeDnsRecordInput adapt(final UpdateDnsRecordRequest updateDNSRequest) {
        return new SynchronizeDnsRecordInput(updateDNSRequest.dns(), updateDNSRequest.type());
    }

}
