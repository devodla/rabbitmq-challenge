package uk.layme.btgpactual.orderms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.layme.btgpactual.orderms.entity.OrderEntity;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {
}
