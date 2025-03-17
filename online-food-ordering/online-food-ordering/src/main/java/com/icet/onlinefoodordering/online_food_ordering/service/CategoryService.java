package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name,Long userId) throws Exception;
    List<Category> findCategoryByRestaurantId(Long id)throws Exception;
    Category findCategoryById(Long id)throws Exception;
}
