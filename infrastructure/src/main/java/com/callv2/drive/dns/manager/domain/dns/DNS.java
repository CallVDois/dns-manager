package com.callv2.drive.dns.manager.domain.dns;

import com.callv2.drive.dns.manager.domain.ip.IP;

public record DNS(String value, Type type) {

    public enum Type {
        A(IP.Type.IPV4, "A"),
        AAAA(IP.Type.IPV6, "AAAA");

        private final IP.Type ipType;
        private final String value;

        Type(IP.Type ipType, String value) {
            this.ipType = ipType;
            this.value = value;
        }

        public IP.Type getIpType() {
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
