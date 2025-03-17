package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.dto.RestaurantDto;
import com.icet.onlinefoodordering.online_food_ordering.model.Address;
import com.icet.onlinefoodordering.online_food_ordering.model.Restaurant;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.repository.AddressRepository;
import com.icet.onlinefoodordering.online_food_ordering.repository.RestaurantRepository;
import com.icet.onlinefoodordering.online_food_ordering.repository.UserRepository;
import com.icet.onlinefoodordering.online_food_ordering.request.CreateRestaurantRequest;
import com.icet.onlinefoodordering.online_food_ordering.service.RestaurantService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address=addressRepository.save(req.getAddress());

        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        if (updateRestaurant.getName() != null) {
            restaurant.setName(updateRestaurant.getName());
        }
        if (updateRestaurant.getDescription() != null) {
            restaurant.setDescription(updateRestaurant.getDescription());
        }
        if (updateRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updateRestaurant.getCuisineType());
        }
        if (updateRestaurant.getAddress() != null) {
            restaurant.setAddress(updateRestaurant.getAddress());
        }
        if (updateRestaurant.getContactInformation() != null) {
            restaurant.setContactInformation(updateRestaurant.getContactInformation());
        }
        if (updateRestaurant.getOpeningHours() != null) {
            restaurant.setOpeningHours(updateRestaurant.getOpeningHours());
        }
        if (updateRestaurant.getImages() != null && !updateRestaurant.getImages().isEmpty()) {
            restaurant.setImages(updateRestaurant.getImages());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt=restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Resturent Not Found With Id"+id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("resturent Not Found with owner id"+userId);
        }
        return restaurant;
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Restaurant restaurant=findRestaurantById(restaurantId);

        RestaurantDto dto=new RestaurantDto();
        dto.setDescription(restaurant.getDescription());
        dto.setImages(restaurant.getImages());
        dto.setTitle(restaurant.getName());
        dto.setId(restaurantId);

        boolean isFavorited = false;

       List<RestaurantDto> favorites=user.getFavorites();
       for(RestaurantDto favorite : favorites){
           if(favorite.getId().equals(restaurantId)){
               isFavorited=true;
               break;
           }
       }
       if(isFavorited){
           favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
       }else{
           favorites.add(dto);
       }

        userRepository.save(user);

        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant=findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
