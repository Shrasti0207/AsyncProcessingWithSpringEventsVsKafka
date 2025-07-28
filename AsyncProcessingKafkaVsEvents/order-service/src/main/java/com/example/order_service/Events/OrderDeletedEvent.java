package com.example.order_service.Events;

import org.springframework.context.ApplicationEvent;

public class OrderDeletedEvent extends ApplicationEvent {

    private final String orderId;

    public OrderDeletedEvent(Object source, String orderId) {
        super(source);
        this.orderId = orderId;
    }

    public String getOrderId() { return orderId; }
}
