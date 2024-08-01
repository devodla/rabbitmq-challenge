package uk.layme.btgpactual.orderms.factory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import uk.layme.btgpactual.orderms.entity.OrderEntity;
import uk.layme.btgpactual.orderms.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {

    public static OrderEntity build() {
        OrderItem items = new OrderItem("notebook", 1, BigDecimal.valueOf(20.50));

        OrderEntity entity = new OrderEntity();
        entity.setOrderId(1L);
        entity.setCustomerId(2L);
        entity.setTotal(BigDecimal.valueOf(20.50));
        entity.setItems(List.of(items));

        return entity;
    }

    public static Page<OrderEntity> buildWithPage() {
        return new PageImpl<>(List.of(build()));
    }
}
