package com.example.inventory_service.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaInventoryListener {

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(String orderId) {
        System.out.println("Received order ID from Kafka: " + orderId);
    }
}
