package com.icet.onlinefoodordering.online_food_ordering.request;

import com.icet.onlinefoodordering.online_food_ordering.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    private Long restaurantId;
    private Address deliveryAddress;

}
