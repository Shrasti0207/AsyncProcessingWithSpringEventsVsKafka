package com.example.order_service.EventTest;
import com.example.order_service.Events.LocalOrderEvent;
import com.example.order_service.Events.OrderCreatedListener;
import com.example.order_service.Service.OrderService;
import com.example.order_service.config.TestSpyConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Import(TestSpyConfig.class)
public class SpringEventIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderCreatedListener orderCreatedListenerSpy;

    @Test
    public void testLocalEventIsHandled() {
        orderService.triggerLocalEvent("Testing local event");
        verify(orderCreatedListenerSpy, timeout(1000)).handleLocalOrderEvent(any(LocalOrderEvent.class));
    }
}
