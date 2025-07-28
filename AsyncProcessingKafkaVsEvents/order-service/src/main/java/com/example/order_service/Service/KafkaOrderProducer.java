package com.example.order_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaOrderProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrder(String orderId){
        kafkaTemplate.send("order-topic", orderId);
        System.out.println("Order sent to Kafka: " + orderId);
    }
}
