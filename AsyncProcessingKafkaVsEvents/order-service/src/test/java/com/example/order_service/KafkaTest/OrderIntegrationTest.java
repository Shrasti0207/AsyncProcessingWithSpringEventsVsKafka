package com.example.order_service.KafkaTest;
import com.example.order_service.Entity.Order;
import com.example.order_service.Service.OrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.apache.kafka.common.serialization.StringDeserializer;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.Map;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "order-topic" }, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    private KafkaConsumer<String, String> consumer;

    @BeforeAll
    public void setupConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka);
        consumerProps.put("key.deserializer", StringDeserializer.class);
        consumerProps.put("value.deserializer", StringDeserializer.class);
        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("order-topic"));
    }

    @Test
    public void testOrderKafkaMessageProduced() {
        Order order = new Order();
        order.setId("order-kafka-1");
        order.setStatus("CREATED");

        orderService.placeOrder(order);

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "order-topic");

        assertThat(record.value()).contains("order-kafka-1");
        System.out.println("✅ Kafka message received in test: " + record.value());
    }

    @AfterAll
    public void cleanup() {
        consumer.close();
    }
}
