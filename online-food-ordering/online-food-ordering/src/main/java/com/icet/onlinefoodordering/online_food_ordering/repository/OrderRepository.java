package com.icet.onlinefoodordering.online_food_ordering.repository;

import com.icet.onlinefoodordering.online_food_ordering.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomerId(Long userId);
    List<Order> findByRestaurantId(Long restaurantId);

}
