package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.model.Cart;
import com.icet.onlinefoodordering.online_food_ordering.repository.CartRepository;
import com.icet.onlinefoodordering.online_food_ordering.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
