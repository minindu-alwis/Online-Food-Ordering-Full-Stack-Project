package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.model.Category;
import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.repository.CategoryRepository;
import com.icet.onlinefoodordering.online_food_ordering.service.CategoryService;
import com.icet.onlinefoodordering.online_food_ordering.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final RestaurantService restaurantService;
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        Category category=new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepository.save(category);

    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        return categoryRepository.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> optionalCategory=categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw new Exception("category not found");
        }
        return optionalCategory.get();
    }
}
