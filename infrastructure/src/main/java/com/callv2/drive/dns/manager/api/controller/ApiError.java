package com.callv2.drive.dns.manager.api.controller;

public record ApiError(String message) {

    static ApiError with(final String message) {
        return new ApiError(message);
    }

}