package com.callv2.dns.manager.adapter.external.cloudflare.model;

import java.util.List;

public record ListDNSResponse(
        boolean success,
        List<Error> errors,
        List<Message> messages,
        List<Result> result) {

    public record Error(
            int code,
            String message) {

    }

    public record Message(
            int code,
            String message) {
    }

    public record Result(
            String comment,
            String content,
            String name,
            boolean proxied,
            Settings settings,
            List<String> tags,
            int ttl,
            String type,
            String id,
            boolean proxiable) {
    }

    public record Settings(
            boolean ipv4Only,
            boolean ipv6Only) {
    }

}
