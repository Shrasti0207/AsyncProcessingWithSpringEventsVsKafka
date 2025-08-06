package com.example.inventory_service.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class KafkaInventoryListener {
    public static final BlockingQueue<String> receivedMessages = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(String orderId) {

        System.out.println("Received order ID from Kafka: " + orderId);
        receivedMessages.offer(orderId);
    }
}
