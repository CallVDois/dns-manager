package com.callv2.dns.manager.infrastructure.record.model;

import com.callv2.dns.manager.domain.record.DnsRecordType;

public record UpdateDnsRecordRequest(String dns, DnsRecordType type) {

}
