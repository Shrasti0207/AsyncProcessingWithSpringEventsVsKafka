package com.example.order_service.Service;

import com.example.order_service.Entity.Order;
import com.example.order_service.Events.LocalOrderEvent;
import com.example.order_service.Events.OrderCreatedEvent;
import com.example.order_service.Events.OrderDeletedEvent;
import com.example.order_service.Events.OrderUpdatedEvent;
import com.example.order_service.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private KafkaOrderProducer kafkaOrderProducer;

    public void placeOrder(Order order){

        order.setStatus("CREATED");
        repository.save(order);
        publisher.publishEvent(new OrderCreatedEvent(this, order));
        kafkaOrderProducer.sendOrder(order.getId());
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Order> getOrder(String orderId) {
        return repository.findById(orderId);
    }

    public Order updateOrder(String orderId, String status) {
        Order order = repository.findById(orderId).orElseThrow();
        order.setStatus(status);
        Order updatedOrder = repository.save(order);
        publisher.publishEvent(new OrderUpdatedEvent(this, orderId, status));

        kafkaOrderProducer.sendOrder("Order updated: " + orderId + " with status: " + status);

        return updatedOrder;
    }

    public void deleteOrder(String orderId) {
        repository.deleteById(orderId);
        publisher.publishEvent(new OrderDeletedEvent(this, orderId));
        kafkaOrderProducer.sendOrder("Order deleted: " + orderId);
    }

    public void triggerLocalEvent(String message) {
        publisher.publishEvent(new LocalOrderEvent(this, message));
    }
}
