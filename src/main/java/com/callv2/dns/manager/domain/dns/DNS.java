package com.callv2.dns.manager.domain.dns;

import com.callv2.dns.manager.domain.record.IpType;

public record DNS(String value, Type type) {

    public enum Type {
        A(IpType.IPV4, "A"),
        AAAA(IpType.IPV6, "AAAA");

        private final IpType ipType;
        private final String value;

        Type(IpType ipType, String value) {
            this.ipType = ipType;
            this.value = value;
        }

        public IpType getIpType() {
            return ipType;
        }

        public String getValue() {
            return value;
        }

        public static Type fromValue(String value) {
            for (Type type : Type.values())
                if (type.getValue().equals(value))
                    return type;

            throw new IllegalArgumentException("Unknown DNS type: " + value);
        }

    }

}
