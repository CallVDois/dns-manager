package com.callv2.dns.manager.adapter.dns.adapter;

import com.callv2.dns.manager.adapter.dns.model.UpdateDNSRequest;
import com.callv2.dns.manager.application.dns.synchronize.SynchronizeDNSInput;

public interface DNSAdapter {

    static SynchronizeDNSInput adapt(final UpdateDNSRequest updateDNSRequest) {
        return new SynchronizeDNSInput(updateDNSRequest.dns(), updateDNSRequest.type());
    }

}
