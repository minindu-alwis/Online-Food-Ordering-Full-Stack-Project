package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.model.Order;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.OrderRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest order, User user) throws Exception;

    Order updateOrder(Long orderId, String orderStatus)throws Exception;

    void cancelOrder(Long orderId)throws Exception;

    List<Order> getUsersOrder(Long userId)throws Exception;

    List<Order> getRestaurantOrder(Long restaurantId,String orderStatus)throws Exception;

    Order findOrderById(Long orderId)throws Exception;


}
