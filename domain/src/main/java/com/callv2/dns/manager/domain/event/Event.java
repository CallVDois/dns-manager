package com.callv2.dns.manager.domain.event;

import java.time.Instant;

public interface Event<D> {

    String id();

    String name();

    String source();

    D data();

    Instant generatedAt();

}
