package com.example.notification_service.ConsumerTests;

import com.example.notification_service.Kafka.KafkaNotificationListener;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"order-topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationKafkaListenerTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private KafkaProducer<String, String> producer;

    @BeforeAll
    void setup() {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
        Properties props = new Properties();
        props.putAll(producerProps);
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);

        producer = new KafkaProducer<>(props);
    }

    @Test
    public void testNotificationConsumerReceivesKafkaMessage() throws InterruptedException {
        String orderId = "notification-test-1";
        producer.send(new ProducerRecord<>("order-topic", orderId));
        String consumed = KafkaNotificationListener.receivedMessages.poll(3, TimeUnit.SECONDS);
        assertThat(consumed).isEqualTo(orderId);
        System.out.println("✅ Sent order to Kafka for notification: " + orderId);
    }

    @AfterAll
    void teardown() {
        producer.close();
    }
}
