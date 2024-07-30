package uk.layme.btgpactual.orderms.factory;

import uk.layme.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import uk.layme.btgpactual.orderms.listener.dto.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent build() {
        OrderItemEvent items = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        return new OrderCreatedEvent(1L, 2L, List.of(items));
    }
}
