package com.callv2.dns.manager.infrastructure.api.controller;

public record ApiError(String message) {

    static ApiError with(final String message) {
        return new ApiError(message);
    }

}