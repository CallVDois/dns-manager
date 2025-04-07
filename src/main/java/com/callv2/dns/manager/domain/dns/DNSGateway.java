package com.callv2.dns.manager.domain.dns;

import com.callv2.dns.manager.domain.ip.IP;

public interface DNSGateway {

    // List<DNS> listAll();

    void updateIP(DNS dns, IP ip);

}
