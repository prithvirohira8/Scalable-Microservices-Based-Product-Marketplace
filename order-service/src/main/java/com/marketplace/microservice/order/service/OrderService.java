package com.marketplace.microservice.order.service;
import com.marketplace.microservice.order.Order.OrderRepository;
import com.marketplace.microservice.order.client.InventoryClient;
import com.marketplace.microservice.order.dto.OrderRequest;
import com.marketplace.microservice.order.event.OrderPlacedEvent;
import com.marketplace.microservice.order.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {
//    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    // Explicit constructor injection instead of using @RequiredArgsConstructor
    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient, KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.kafkaTemplate = kafkaTemplate;
    }

//    public void placeOrder(OrderRequest orderRequest) {
//        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
//        if (isProductInStock){
//            Order order = new Order();
//            order.setOrderNumber(UUID.randomUUID().toString());
//            order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
//            order.setSkuCode(orderRequest.skuCode());
//            order.setQuantity(orderRequest.quantity());
//            orderRepository.save(order);
//
//            // Send the message to Kafka Topic
////            OrderPlacedEvent orderPlacedEvent = newOrderPlacedEvent
////            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
////            orderPlacedEvent.setOrderNumber(order.getOrderNumber());
////            orderPlacedEvent.setEmail(orderRequest.userDetails().email());
////            orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
////            orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
////            log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
////            kafkaTemplate.send("order-placed", orderPlacedEvent);
////            log.info("End - Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
//        } else {
//            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is not in stock");
//        }
//    }

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
            var orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails()
                    .email(), "test3", "user3");
//                    orderRequest.userDetails()
//                            .firstName(),
//                    orderRequest.userDetails()
//                            .lastName());
            log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
        } else {
            System.out.println(isProductInStock);
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() + " is not in stock");
        }
    }

}