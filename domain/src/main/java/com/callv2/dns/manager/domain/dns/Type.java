package com.callv2.dns.manager.domain.dns;

public enum Type {
    A(com.callv2.dns.manager.domain.ip.Type.IPV4, "A"),
    AAAA(com.callv2.dns.manager.domain.ip.Type.IPV6, "AAAA");

    private final com.callv2.dns.manager.domain.ip.Type ipType;
    private final String value;

    Type(com.callv2.dns.manager.domain.ip.Type ipType, String value) {
        this.ipType = ipType;
        this.value = value;
    }

    public com.callv2.dns.manager.domain.ip.Type getIpType() {
        return ipType;
    }

    public String getValue() {
        return value;
    }

    public static Type fromValue(String value) {
        for (Type type : Type.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown DNS type: " + value);
    }

}
