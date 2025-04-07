package com.callv2.drive.dns.manager.adapter.dns;

import java.util.List;

import com.callv2.drive.dns.manager.domain.dns.DNS;
import com.callv2.drive.dns.manager.domain.dns.DNSGateway;
import com.callv2.drive.dns.manager.domain.ip.IP;

public class CloudflareDNSGateway implements DNSGateway {

    @Override
    public List<DNS> listAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listAll'");
    }

    @Override
    public void updateIP(DNS dns, IP ip) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateIP'");
    }

}
