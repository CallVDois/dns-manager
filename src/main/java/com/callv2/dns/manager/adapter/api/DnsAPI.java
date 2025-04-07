package com.callv2.dns.manager.adapter.api;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.callv2.dns.manager.adapter.dns.model.UpdateDNSRequest;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DNS")
@RequestMapping("dns")
public interface DnsAPI {

    @PostMapping("force-update")
    void forceUpdate(final List<UpdateDNSRequest> dnsList);

}
