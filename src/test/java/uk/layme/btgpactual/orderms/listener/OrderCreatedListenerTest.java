package uk.layme.btgpactual.orderms.listener;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import uk.layme.btgpactual.orderms.factory.OrderCreatedEventFactory;
import uk.layme.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import uk.layme.btgpactual.orderms.service.OrderService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderCreatedListenerTest {

    @Mock
    OrderService orderService;

    @InjectMocks
    OrderCreatedListener orderCreatedListener;

    @Nested
    class Listen {

        @Test
        void shouldCallServiceWithCorrectParameters() {
            OrderCreatedEvent event = OrderCreatedEventFactory.build();
            Message<OrderCreatedEvent> message = MessageBuilder.withPayload(event).build();

            orderCreatedListener.listen(message);

            verify(orderService, times(1)).save(eq(message.getPayload()));
        }
    }
}