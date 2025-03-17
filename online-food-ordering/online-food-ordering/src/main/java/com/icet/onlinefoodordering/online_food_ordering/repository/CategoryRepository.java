package com.icet.onlinefoodordering.online_food_ordering.repository;

import com.icet.onlinefoodordering.online_food_ordering.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    public List<Category> findByRestaurantId(Long id);

}
