package com.callv2.dns.manager.adapter.record.model;

import com.callv2.dns.manager.domain.record.DnsRecordType;

public record UpdateDnsRecordRequest(String dns, DnsRecordType type) {

}
