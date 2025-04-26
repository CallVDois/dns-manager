package com.callv2.dns.manager.domain.record;

public enum DnsRecordType {

    A(IpType.IPV4, "A"),
    AAAA(IpType.IPV6, "AAAA");

    private final IpType ipType;
    private final String value;

    DnsRecordType(IpType ipType, String value) {
        this.ipType = ipType;
        this.value = value;
    }

    public IpType getIpType() {
        return ipType;
    }

    public String getValue() {
        return value;
    }

    public static DnsRecordType fromValue(final String value) {
        for (DnsRecordType type : DnsRecordType.values())
            if (type.getValue().equals(value))
                return type;

        throw new IllegalArgumentException("Unknown Record type: " + value);
    }

}
