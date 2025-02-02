package com.callv2.drive.dns.manager.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DNS")
@RequestMapping("dns")
public interface DnsAPI {

    @PostMapping("force-update")
    void forceUpdate();

}
