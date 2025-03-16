package com.icet.onlinefoodordering.online_food_ordering.response;

import com.icet.onlinefoodordering.online_food_ordering.Util.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponce {

    private String jwt;
    private String message;
    private USER_ROLE role;
}
