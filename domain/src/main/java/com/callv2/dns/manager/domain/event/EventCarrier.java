package com.callv2.dns.manager.domain.event;

import java.util.Optional;

public interface EventCarrier {

    Optional<Event<?>> dequeueEvent();

}
