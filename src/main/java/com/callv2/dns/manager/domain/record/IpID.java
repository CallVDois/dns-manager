package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.Identifier;

public class IpID extends Identifier<String> {

    private IpID(String id) {
        super(id);
    }

    public static IpID from(final String ipValue) {
        return new IpID(ipValue);
    }

}
