package com.callv2.dns.manager.application.record.synchronize;

import com.callv2.dns.manager.domain.record.DnsRecordType;

public record SynchronizeDnsRecordInput(String dns, DnsRecordType type) {

}
