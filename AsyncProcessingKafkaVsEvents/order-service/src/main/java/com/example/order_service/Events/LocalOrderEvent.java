package com.example.order_service.Events;

import org.springframework.context.ApplicationEvent;

public class LocalOrderEvent extends ApplicationEvent {
    private final String message;

    public LocalOrderEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
