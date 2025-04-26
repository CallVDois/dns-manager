package com.callv2.dns.manager.domain.validation;

import com.callv2.dns.manager.domain.exception.DomainException;

public record ValidationError(String message) {

    public static ValidationError with(final String message) {
        return new ValidationError(message);
    }

    public static ValidationError from(final DomainException.Error domainError) {
        return new ValidationError(domainError.message());
    }

    public static DomainException.Error toDomain(final ValidationError error) {
        return new DomainException.Error(error.message());
    }

}