package com.icet.onlinefoodordering.online_food_ordering.request;

import com.icet.onlinefoodordering.online_food_ordering.model.Address;
import com.icet.onlinefoodordering.online_food_ordering.model.ContactInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRestaurantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}
