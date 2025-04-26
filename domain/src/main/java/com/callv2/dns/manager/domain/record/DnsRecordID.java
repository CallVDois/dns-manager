package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.Identifier;

public class DnsRecordID extends Identifier<String> {

    private DnsRecordID(final String id) {
        super(id);
    }

    public static DnsRecordID from(final DnsRecordName recordName, final DnsRecordType recordType) {
        return new DnsRecordID(recordName.value() + "_" + recordType.name());
    }

}
