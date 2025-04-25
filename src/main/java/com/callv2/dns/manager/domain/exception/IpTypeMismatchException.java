package com.callv2.dns.manager.domain.exception;

import java.util.List;

import com.callv2.dns.manager.domain.record.IpType;

public class IpTypeMismatchException extends DomainException {

    private static final String MESSAGE = "IP type mismatch. Expected %s but got %s";

    private IpTypeMismatchException(final IpType expected, final IpType actual) {
        super(String.format(MESSAGE, expected, actual), List.of());
    }

    public static IpTypeMismatchException with(final IpType expected, final IpType actual) {
        return new IpTypeMismatchException(expected, actual);
    }

}
