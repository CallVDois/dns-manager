package com.callv2.dns.manager.domain.exception;

import java.util.List;

import com.callv2.dns.manager.domain.validation.ValidationError;
import com.callv2.dns.manager.domain.validation.ValidationHandler;

public class ValidationException extends DomainException {

    private ValidationException(final String aMessage, final List<ValidationError> anErrors) {
        super(aMessage, List.copyOf(anErrors));
    }

    public static ValidationException with(final String aMessage, final ValidationHandler aNotification) {
        return new ValidationException(aMessage, List.copyOf(aNotification.getErrors()));
    }

    public static ValidationException with(final String message, final ValidationError error) {
        return new ValidationException(message, List.of(error));
    }

}
