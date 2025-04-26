package com.callv2.dns.manager.domain.record;

import com.callv2.dns.manager.domain.validation.ValidationError;
import com.callv2.dns.manager.domain.validation.ValidationHandler;
import com.callv2.dns.manager.domain.validation.Validator;

public class IpValidator extends Validator {

    private final Ip ip;

    protected IpValidator(final ValidationHandler handler, final Ip ip) {
        super(handler);
        this.ip = ip;
    }

    @Override
    public void validate() {

        if (ip == null) {
            validationHandler().append(ValidationError.with("IP cannot be null"));
            return;
        }

        if (ip.getValue() == null || ip.getValue().isEmpty())
            validationHandler().append(ValidationError.with("IP value cannot be null or empty"));

        if (ip.getType() == null)
            validationHandler().append(ValidationError.with("IP type cannot be null"));

        if (ip.getValue() != null) {
            if (IpType.IPV4.equals(ip.getType()))
                validateIpv4();
            else if (IpType.IPV6.equals(ip.getType()))
                validateIpv6();
            else
                validationHandler().append(ValidationError.with("Invalid IP type"));
        }

    }

    private void validateIpv4() {

        String[] parts = ip.getValue().split("\\.");
        if (parts.length != 4) {
            validationHandler().append(ValidationError.with("Invalid IPv4 format"));
            return;
        }

        for (String part : parts) {
            try {
                int segment = Integer.parseInt(part);
                if (segment < 0 || segment > 255) {
                    validationHandler().append(ValidationError.with("IPv4 segments must be between 0 and 255"));
                    return;
                }
            } catch (NumberFormatException e) {
                validationHandler().append(ValidationError.with("IPv4 segments must be numeric"));
                return;
            }
        }

    }

    private void validateIpv6() {

        String[] parts = ip.getValue().split(":");
        if (parts.length < 3 || parts.length > 8) {
            validationHandler().append(ValidationError.with("Invalid IPv6 format"));
            return;
        }

        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            if (part.length() > 4) {
                validationHandler()
                        .append(ValidationError.with("IPv6 segments must not exceed 4 hexadecimal characters"));
                return;
            }
            try {
                Integer.parseInt(part, 16);
            } catch (NumberFormatException e) {
                validationHandler().append(ValidationError.with("IPv6 segments must be valid hexadecimal numbers"));
                return;
            }
        }

    }
}
