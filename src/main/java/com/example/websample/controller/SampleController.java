package com.example.websample.controller;

import com.example.websample.dto.ErrorResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SampleController {

    @GetMapping(value = "/order/{orderId}")
    public String getOrder(@PathVariable String orderId) throws IllegalAccessException {
        log.info("Get same order : " + orderId);

        // 인터셉터의 afterComplete test
        if ("500".equals(orderId)) {
            throw new IllegalAccessException("500은 올바른 orderId가 아닙니다.");
        }
        return "orderID:" + orderId + ", orderAccount:1000";
    }

    // @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ErrorResponse> handleIllegalAccessException(IllegalAccessException e){
        log.error("IllegalAccessException is occured.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .header("newHeader", "Some Value")
                .body(new ErrorResponse("INVALID_ACCESS",
                        "IllegalAccessException is occured."));
    }

    @GetMapping(value = "/order")
    public String getOrderWithRequestPram(@RequestParam("orderId") String id,
          @RequestParam(name="orderAmount", required=false, defaultValue = "1000") String amount) {
        log.info("Get same order : " + id);
        return "orderId:" + id + ", orderAmount:" + amount;
    }

    @PostMapping(value = "/order")
    public String createOrder(
            @RequestBody CreateOrderRequest createOrderRequest,
            @RequestHeader String userAccountId) {
        log.info("Create order : " + createOrderRequest + "," +
                " userAccountId " + userAccountId);
        return "orderId:" + createOrderRequest.getOrderId() + ", " +
                "orderAmount:" + createOrderRequest.getOrderAmount();
    }

    @Data
    public static class CreateOrderRequest {
        private String orderId;
        private Integer orderAmount;
    }
}
