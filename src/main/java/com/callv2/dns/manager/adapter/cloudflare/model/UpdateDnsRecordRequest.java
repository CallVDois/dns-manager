package com.callv2.dns.manager.adapter.cloudflare.model;

import java.util.List;

public record UpdateDnsRecordRequest(
        String comment,
        String content,
        String name,
        Boolean proxied,
        Settings settings,
        List<String> tags,
        Integer ttl,
        String type) {

    public record Settings(
            Boolean ipv4Only,
            Boolean ipv6Only) {
    }

}