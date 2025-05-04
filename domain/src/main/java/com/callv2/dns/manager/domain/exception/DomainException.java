package com.callv2.dns.manager.domain.exception;

import java.util.List;

public class DomainException extends NoStacktraceException {

    protected final List<DomainException.Error> errors;

    protected DomainException(final String message, final List<DomainException.Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final DomainException.Error error) {
        return new DomainException(error.message(), List.of(error));
    }

    public static DomainException with(final String message, final List<DomainException.Error> errors) {
        return new DomainException(message, errors);
    }

    public List<DomainException.Error> getErrors() {
        return errors;
    }

    public record Error(String message) {

        public static Error with(final String message) {
            return new Error(message);
        }

    }

}
