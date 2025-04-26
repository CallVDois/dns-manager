package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.ValueObject;

public record DnsRecordName(String value) implements ValueObject {

    public static DnsRecordName of(final String recordName) {
        return new DnsRecordName(recordName);
    }

}
