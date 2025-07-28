package com.example.notification_service.Kafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaNotificationListener {
    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consume(String orderId) {
        System.out.println("[Notification] Sending notification for order: " + orderId);
    }
}
