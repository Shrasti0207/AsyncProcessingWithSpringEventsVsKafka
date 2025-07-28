package com.example.order_service.config;

import com.example.order_service.Events.OrderCreatedListener;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestSpyConfig {

    @Bean
    @Primary
    public OrderCreatedListener orderCreatedListenerSpy() {
        return Mockito.spy(new OrderCreatedListener());
    }
}
