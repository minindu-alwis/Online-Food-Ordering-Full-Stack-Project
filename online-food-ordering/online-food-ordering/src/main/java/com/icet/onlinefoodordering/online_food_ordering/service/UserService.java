package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.model.User;

public interface UserService {
    User save(User createdUser);

    User findByEmail(String email) throws Exception;

    User findUserByJwtToken(String jwt) throws Exception;
}
