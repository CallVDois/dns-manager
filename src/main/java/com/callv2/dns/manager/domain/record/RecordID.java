package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.Identifier;

public class RecordID extends Identifier<String> {

    private RecordID(final String id) {
        super(id);
    }

    public static RecordID from(final RecordName recordName, final RecordType recordType) {
        return new RecordID(recordName.value() + "_" + recordType.name());
    }

}
