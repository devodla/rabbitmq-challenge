package uk.layme.btgpactual.orderms.factory;

import uk.layme.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import uk.layme.btgpactual.orderms.listener.dto.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent buildWithOneItem() {
        OrderItemEvent items = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        return new OrderCreatedEvent(1L, 2L, List.of(items));
    }

    public static OrderCreatedEvent buildWithTwoItems() {
        OrderItemEvent item1 = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        OrderItemEvent item2 = new OrderItemEvent("mouse", 1, BigDecimal.valueOf(35.25));
        return new OrderCreatedEvent(1L, 2L, List.of(item1, item2));
    }
}
