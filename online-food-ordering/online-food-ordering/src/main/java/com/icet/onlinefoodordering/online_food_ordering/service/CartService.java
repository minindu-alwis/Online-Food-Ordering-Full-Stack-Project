package com.icet.onlinefoodordering.online_food_ordering.service;

import com.icet.onlinefoodordering.online_food_ordering.model.Cart;
import com.icet.onlinefoodordering.online_food_ordering.model.CartItem;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.AddCartItemRequest;

public interface CartService {
    void save(Cart cart);

    CartItem addItemToCart(AddCartItemRequest req, String jwt)throws Exception;

    CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;

    Cart removeItemFormCart(Long cartItemId,String jwt)throws Exception;

    Long calculateCartTotals(Cart cart)throws Exception;

    Cart findCartById(Long id)throws Exception;

    Cart findCartByUserId(String jwt)throws Exception;

    Cart clearCart(String jwt)throws Exception;





}
