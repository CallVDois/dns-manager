package com.callv2.drive.dns.manager.domain.dns;

import java.util.List;

import com.callv2.drive.dns.manager.domain.ip.IP;

public interface DNSGateway {

    List<DNS> listAll();

    void updateIP(DNS dns, IP ip);

}
