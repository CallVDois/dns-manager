package com.callv2.dns.manager.adapter.dns.model;

import com.callv2.dns.manager.domain.record.DnsRecordType;

public record UpdateDNSRequest(String dns, DnsRecordType type) {

}
