package com.icet.onlinefoodordering.online_food_ordering.repository;

import com.icet.onlinefoodordering.online_food_ordering.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
