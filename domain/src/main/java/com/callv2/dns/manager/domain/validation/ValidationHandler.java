package com.callv2.dns.manager.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(final ValidationError error);

    ValidationHandler append(final ValidationHandler handler);

    <T> T validate(final Validation<T> valdidation);

    List<ValidationError> getErrors();

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    @FunctionalInterface
    public interface Validation<T> {

        T validate();

    }

}
