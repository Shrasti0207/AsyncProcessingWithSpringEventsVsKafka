package com.example.notification_service.Kafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class KafkaNotificationListener {

    public static final BlockingQueue<String> receivedMessages = new LinkedBlockingQueue<>();
    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consume(String orderId) {
        System.out.println("[Notification] Sending notification for order: " + orderId);
        receivedMessages.offer(orderId);
    }
}
