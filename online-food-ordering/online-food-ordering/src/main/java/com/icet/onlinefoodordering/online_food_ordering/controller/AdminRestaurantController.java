package com.icet.onlinefoodordering.online_food_ordering.controller;

import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.CreateRestaurantRequest;
import com.icet.onlinefoodordering.online_food_ordering.response.MessageResponse;
import com.icet.onlinefoodordering.online_food_ordering.service.RestaurantService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurant")
public class AdminRestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.createRestaurant(req,user);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@RequestBody CreateRestaurantRequest req, @RequestHeader("Authorization")String jwt,@PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant=restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization")String jwt,
                                                       @PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(id);
        MessageResponse res=new MessageResponse();
        res.setMessage("resturent Deleted Succsessfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(@RequestBody CreateRestaurantRequest req,
                                                            @RequestHeader("Authorization")String jwt,
                                                            @PathVariable Long id) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(@RequestBody CreateRestaurantRequest req,
                                                             @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }



}
