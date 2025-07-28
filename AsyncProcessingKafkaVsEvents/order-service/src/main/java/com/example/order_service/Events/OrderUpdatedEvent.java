package com.example.order_service.Events;

import org.springframework.context.ApplicationEvent;

public class OrderUpdatedEvent extends ApplicationEvent {

    private final String orderId;
    private final String status;

    public OrderUpdatedEvent(Object source, String orderId, String status) {
        super(source);
        this.orderId = orderId;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getStatus() { return status; }
}
