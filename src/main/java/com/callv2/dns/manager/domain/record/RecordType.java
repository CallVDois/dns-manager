package com.callv2.dns.manager.domain.record;

public enum RecordType {

    A(IpType.IPV4, "A"),
    AAAA(IpType.IPV6, "AAAA");

    private final IpType ipType;
    private final String value;

    RecordType(IpType ipType, String value) {
        this.ipType = ipType;
        this.value = value;
    }

    public IpType getIpType() {
        return ipType;
    }

    public String getValue() {
        return value;
    }

    public static RecordType fromValue(final String value) {
        for (RecordType type : RecordType.values())
            if (type.getValue().equals(value))
                return type;

        throw new IllegalArgumentException("Unknown Record type: " + value);
    }

}
