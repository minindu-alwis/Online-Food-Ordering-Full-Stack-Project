package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.config.JwtProvider;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.repository.UserRepository;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User save(User createdUser) {
        return userRepository.save(createdUser);
    }

    @Override
    public User findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user=findByEmail(email);
        return user;
    }
}
