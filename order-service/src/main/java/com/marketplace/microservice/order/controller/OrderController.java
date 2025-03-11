package com.marketplace.microservice.order.controller;

import com.marketplace.microservice.order.dto.OrderRequest;
import com.marketplace.microservice.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    // Explicit constructor injection instead of @RequiredArgsConstructor
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        logger.info("Order Placed Successfully");
        return "Order Placed Successfully";
    }
}