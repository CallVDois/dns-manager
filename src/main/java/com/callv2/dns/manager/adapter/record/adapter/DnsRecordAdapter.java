package com.callv2.dns.manager.adapter.record.adapter;

import com.callv2.dns.manager.adapter.record.model.UpdateDnsRecordRequest;
import com.callv2.dns.manager.application.record.synchronize.SynchronizeDnsRecordInput;

public interface DnsRecordAdapter {

    static SynchronizeDnsRecordInput adapt(final UpdateDnsRecordRequest updateDNSRequest) {
        return new SynchronizeDnsRecordInput(updateDNSRequest.dns(), updateDNSRequest.type());
    }

}
