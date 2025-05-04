package com.callv2.dns.manager.domain;

import com.callv2.dns.manager.domain.validation.ValidationHandler;

public interface ValueObject {

    default void validate(ValidationHandler handler) {
    };

}
