package com.callv2.dns.manager.adapter.dns.model;

import com.callv2.dns.manager.domain.dns.DNS;

public record UpdateDNSRequest(String dns, DNS.Type type) {

}
