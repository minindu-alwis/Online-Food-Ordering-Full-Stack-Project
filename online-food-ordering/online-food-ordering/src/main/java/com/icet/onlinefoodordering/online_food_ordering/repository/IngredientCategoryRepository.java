package com.icet.onlinefoodordering.online_food_ordering.repository;

import com.icet.onlinefoodordering.online_food_ordering.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory,Long> {

    List<IngredientCategory> findByRestaurantId(Long id);

}
