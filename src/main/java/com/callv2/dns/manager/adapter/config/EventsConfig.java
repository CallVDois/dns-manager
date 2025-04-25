package com.callv2.dns.manager.adapter.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.callv2.dns.manager.domain.event.EventDispatcher;
import com.callv2.dns.manager.domain.event.EventHandler;

@Configuration
public class EventsConfig {

    @Bean
    EventDispatcher eventDispatcher(final List<EventHandler<?>> handlers) {
        final EventDispatcher eventDispatcher = new EventDispatcher();
        handlers.forEach(handler -> eventDispatcher.register(handler));
        return eventDispatcher;
    }

}
