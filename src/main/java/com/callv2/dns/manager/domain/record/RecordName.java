package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.ValueObject;

public record RecordName(String value) implements ValueObject {

    public static RecordName of(final String recordName) {
        return new RecordName(recordName);
    }

}
