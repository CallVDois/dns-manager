package com.callv2.dns.manager.infrastructure.external.cloudflare.model;

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

	public static UpdateDnsRecordRequest ofContent(final String content) {
		return new UpdateDnsRecordRequest(
				null,
				content,
				null,
				null,
				null,
				null,
				null,
				null);
	}

	public record Settings(
			Boolean ipv4Only,
			Boolean ipv6Only) {
	}

}