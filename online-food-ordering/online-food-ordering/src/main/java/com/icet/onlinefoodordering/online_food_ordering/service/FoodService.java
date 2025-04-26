package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.model.Category;
import com.icet.onlinefoodordering.online_food_ordering.model.Food;
import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    void deleteFood(Long foodId)throws Exception;
    List<Food> getRestaurantFood(Long restaurantId, boolean isVegitarain, boolean isNonVeg, boolean isSeasonal, String foodCategory);
    List<Food> searchFood(String keyword);
    Food findFoodById(Long foodId)throws Exception;
    Food updateAvailibilityStatus(Long foodId)throws Exception;
    List<Food> getRestaurantFoodsWithNoReq(Long restaurantId);



}
