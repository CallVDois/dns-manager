package com.callv2.dns.manager.domain.dns;

import com.callv2.dns.manager.domain.record.Ip;

public interface DNSGateway {

    // List<DNS> listAll();

    void updateIP(DNS dns, Ip ip);

}
