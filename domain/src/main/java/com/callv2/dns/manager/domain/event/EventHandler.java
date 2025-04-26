package com.callv2.dns.manager.domain.event;

public interface EventHandler<D> {

    String eventName();

    void handle(Event<D> event);

}
