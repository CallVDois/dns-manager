package com.callv2.dns.manager.application.dns.synchronize;

import com.callv2.dns.manager.domain.dns.DNS;

public record SynchronizeDNSInput(String dns, DNS.Type type) {

}
