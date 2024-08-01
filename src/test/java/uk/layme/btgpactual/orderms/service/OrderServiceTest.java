package uk.layme.btgpactual.orderms.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import uk.layme.btgpactual.orderms.controller.dto.OrderResponse;
import uk.layme.btgpactual.orderms.entity.OrderEntity;
import uk.layme.btgpactual.orderms.factory.OrderCreatedEventFactory;
import uk.layme.btgpactual.orderms.factory.OrderEntityFactory;
import uk.layme.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import uk.layme.btgpactual.orderms.repository.OrderRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Captor
    ArgumentCaptor<OrderEntity> orderEntityCaptor;

    @Nested
    class Save {

        @Test
        void shouldCallRepositorySave() {
            OrderCreatedEvent event = OrderCreatedEventFactory.buildWithOneItem();

            orderService.save(event);

            verify(orderRepository, times(1)).save(any());
        }

        @Test
        void shouldMapEventToEntityWithSuccess() {
            OrderCreatedEvent event = OrderCreatedEventFactory.buildWithOneItem();

            orderService.save(event);

            verify(orderRepository, times(1)).save(orderEntityCaptor.capture());

            OrderEntity entity = orderEntityCaptor.getValue();

            assertEquals(event.codigoPedido(), entity.getOrderId());
            assertEquals(event.codigoCliente(), entity.getCustomerId());
            assertNotNull(entity.getTotal());
            assertEquals(event.itens().getFirst().produto(), entity.getItems().getFirst().getProduct());
            assertEquals(event.itens().getFirst().quantidade(), entity.getItems().getFirst().getQuantity());
            assertEquals(event.itens().getFirst().preco(), entity.getItems().getFirst().getPrice());
        }

        @Test
        void shouldCalculateOrderTotalWithSuccess() {
            OrderCreatedEvent event = OrderCreatedEventFactory.buildWithTwoItems();
            BigDecimal totalItem1 = event.itens().getFirst().preco().multiply(BigDecimal.valueOf(event.itens().getFirst().quantidade()));
            BigDecimal totalItem2 = event.itens().getLast().preco().multiply(BigDecimal.valueOf(event.itens().getLast().quantidade()));
            BigDecimal orderTotal = totalItem1.add(totalItem2);

            orderService.save(event);

            verify(orderRepository, times(1)).save(orderEntityCaptor.capture());

            OrderEntity entity = orderEntityCaptor.getValue();

            assertNotNull(entity.getTotal());
            assertEquals(orderTotal, entity.getTotal());
        }
    }

    @Nested
    class FindAllByCustomerId {

        @Test
        void shouldCallRepository() {
            Long customerId = 1L;
            PageRequest pageRequest = PageRequest.of(0, 10);

            doReturn(OrderEntityFactory.buildWithPage())
                    .when(orderRepository)
                    .findAllByCustomerId(eq(customerId), eq(pageRequest));

            orderService.findAllByCustomerId(customerId, pageRequest);

            verify(orderRepository, times(1)).findAllByCustomerId(eq(customerId), eq(pageRequest));
        }

        @Test
        void shouldMapResponse() {
            Long customerId = 1L;
            PageRequest pageRequest = PageRequest.of(0, 10);
            Page<OrderEntity> page = OrderEntityFactory.buildWithPage();

            doReturn(page)
                    .when(orderRepository)
                    .findAllByCustomerId(anyLong(), any());

            Page<OrderResponse> response = orderService.findAllByCustomerId(customerId, pageRequest);

            assertEquals(page.getTotalPages(), response.getTotalPages());
            assertEquals(page.getTotalElements(), response.getTotalElements());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getNumber(), response.getNumber());

            assertEquals(page.getContent().getFirst().getOrderId(), response.getContent().getFirst().orderId());
            assertEquals(page.getContent().getFirst().getCustomerId(), response.getContent().getFirst().customerId());
            assertEquals(page.getContent().getFirst().getTotal(), response.getContent().getFirst().total());
        }
    }
}