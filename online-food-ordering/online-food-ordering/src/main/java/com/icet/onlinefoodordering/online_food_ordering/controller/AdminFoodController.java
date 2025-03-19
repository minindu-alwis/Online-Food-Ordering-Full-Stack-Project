package com.icet.onlinefoodordering.online_food_ordering.controller;

import com.icet.onlinefoodordering.online_food_ordering.model.Food;
import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.CreateFoodRequest;
import com.icet.onlinefoodordering.online_food_ordering.response.MessageResponse;
import com.icet.onlinefoodordering.online_food_ordering.service.FoodService;
import com.icet.onlinefoodordering.online_food_ordering.service.RestaurantService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    private final FoodService foodService;

    private final UserService userService;

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food=foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("Food Deleted Successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvailibilityStatus(id);


        return new ResponseEntity<>(food, HttpStatus.CREATED);

    }
}
