package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.model.IngredientCategory;
import com.icet.onlinefoodordering.online_food_ordering.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;

    IngredientCategory findIngredientCategoryById(Long id)throws Exception;

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id)throws Exception;

    IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;


    List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    IngredientsItem updateStock(Long id) throws Exception;



}
