package com.callv2.drive.dns.manager.adapter.dns.model;

import com.callv2.drive.dns.manager.domain.dns.DNS;

public record UpdateDNSRequest(String dns, DNS.Type type) {

}
