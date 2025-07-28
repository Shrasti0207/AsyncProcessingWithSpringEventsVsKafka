package com.example.order_service.Events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    @Async
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event){
        System.out.println("Order created with ID: " + event.getOrder());
    }

    @Async
    @EventListener
    public void handleOrderUpdated(OrderUpdatedEvent event) {
        System.out.println("Spring Event: Order updated - " + event.getOrderId());
    }

    @Async
    @EventListener
    public void handleOrderDeleted(OrderDeletedEvent event) {
        System.out.println("Spring Event: Order deleted - " + event.getOrderId());
    }

    @Async
    @EventListener
    public void handleLocalOrderEvent(LocalOrderEvent event) {
        System.out.println("Handled LocalOrderEvent asynchronously: " + event.getMessage());
    }
}
