package com.example.websample.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SampleController {

    @GetMapping(value = "/order/{orderId}")
    public String getOrder(@PathVariable String orderId) {
        log.info("Get same order : " + orderId);
        return "orderID:" + orderId + ", orderAccount:1000";
    }

    @GetMapping(value = "/order")
    public String getOrderWithRequestPram(@RequestParam("orderId") String id,
                                          @RequestParam("orderAmount") String amount) {
        log.info("Get same order : " + id);
        return "orderId:" + id + ", orderAmount:" + amount;
    }

    @PostMapping(value = "/order")
    public String createOrder(
            @RequestBody CreateOrderRequest createOrderRequest,
            @RequestHeader String userAccountId) {
        log.info("Create order : " + createOrderRequest + ","+
                " userAccountId " + userAccountId);
        return "orderId:" + createOrderRequest.getOrderId() + ", " +
                "orderAmount:" + createOrderRequest.getOrderAmount();
    }

    @Data
    public static class CreateOrderRequest{
        private String orderId;
        private Integer orderAmount;
    }
}
