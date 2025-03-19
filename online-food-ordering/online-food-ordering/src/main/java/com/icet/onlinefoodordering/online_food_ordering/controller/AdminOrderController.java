package com.icet.onlinefoodordering.online_food_ordering.controller;

import com.icet.onlinefoodordering.online_food_ordering.model.Order;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.OrderRequest;
import com.icet.onlinefoodordering.online_food_ordering.service.OrderService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    private final UserService userService;


    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long id,@RequestParam(required = false) String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.getRestaurantOrder(id,order_status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,@PathVariable String orderStatus,
                                                       @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Order order = orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}
