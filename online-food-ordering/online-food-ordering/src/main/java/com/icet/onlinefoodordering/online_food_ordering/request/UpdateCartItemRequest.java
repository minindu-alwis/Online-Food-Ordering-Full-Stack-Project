package com.icet.onlinefoodordering.online_food_ordering.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {

    private Long cartItemId;
    private int quantity;

}
