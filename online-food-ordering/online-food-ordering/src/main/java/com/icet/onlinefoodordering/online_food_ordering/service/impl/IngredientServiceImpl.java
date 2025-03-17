package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.model.IngredientCategory;
import com.icet.onlinefoodordering.online_food_ordering.model.IngredientsItem;
import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.repository.IngredientCategoryRepository;
import com.icet.onlinefoodordering.online_food_ordering.repository.IngredientItemRepository;
import com.icet.onlinefoodordering.online_food_ordering.service.IngredientsService;
import com.icet.onlinefoodordering.online_food_ordering.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientsService {

    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientItemRepository ingredientItemRepository;

    private final RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category=new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);
        return ingredientCategoryRepository.save(category);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt=ingredientCategoryRepository.findById(id);

        if(opt.isEmpty()){
            throw new Exception("Ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryRepository.findByRestaurantId(id);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(restaurantId);
        IngredientCategory category=findIngredientCategoryById(categoryId);

        IngredientsItem item=new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredients=ingredientItemRepository.save(item);
        category.getIngredients().add(ingredients);
        return ingredients;
    }

    @Override
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem=ingredientItemRepository.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient not found");
        }

        IngredientsItem ingredientsItem=optionalIngredientsItem.get();
        ingredientsItem.setInStock(!ingredientsItem.isInStock());

        return ingredientItemRepository.save(ingredientsItem);

    }
}
