package com.icet.onlinefoodordering.online_food_ordering.repository;

import com.icet.onlinefoodordering.online_food_ordering.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem,Long> {

    List<IngredientsItem> findByRestaurantId(Long id);
}
