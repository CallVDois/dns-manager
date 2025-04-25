package com.callv2.dns.manager.application.dns.synchronize;

import com.callv2.dns.manager.domain.record.DnsRecordType;

public record SynchronizeDNSInput(String dns, DnsRecordType type) {

}
