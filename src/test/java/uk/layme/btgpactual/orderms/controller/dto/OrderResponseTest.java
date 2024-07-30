package uk.layme.btgpactual.orderms.controller.dto;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import uk.layme.btgpactual.orderms.entity.OrderEntity;
import uk.layme.btgpactual.orderms.factory.OrderEntityFactory;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Nested
    class FromEntity {

        @Test
        void shouldMapCorrectly() {
            OrderEntity input = OrderEntityFactory.build();

            OrderResponse output = OrderResponse.fromEntity(input);

            assertEquals(input.getOrderId(), output.orderId());
            assertEquals(input.getCustomerId(), output.customerId());
            assertEquals(input.getTotal(), output.total());
        }
    }
}
