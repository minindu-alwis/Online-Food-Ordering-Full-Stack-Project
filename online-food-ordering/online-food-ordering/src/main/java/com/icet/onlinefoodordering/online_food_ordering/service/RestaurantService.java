package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.dto.RestaurantDto;
import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    Restaurant createRestaurant(CreateRestaurantRequest req, User user);
    Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updateRestaurant)throws Exception;
    List<Restaurant> getAllRestaurant();
    void deleteRestaurant(Long restaurantId)throws Exception;
    List<Restaurant> searchRestaurant(String keyword);
    Restaurant findRestaurantById(Long id)throws Exception;
    Restaurant getRestaurantByUserId(Long userId)throws Exception;
    RestaurantDto addToFavorites(Long restaurantId,User user)throws Exception;
    Restaurant updateRestaurantStatus(Long id)throws Exception;
}
