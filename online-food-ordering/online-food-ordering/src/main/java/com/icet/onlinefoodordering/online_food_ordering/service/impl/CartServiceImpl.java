package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.model.Cart;
import com.icet.onlinefoodordering.online_food_ordering.model.CartItem;
import com.icet.onlinefoodordering.online_food_ordering.model.Food;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.repository.CartItemRepository;
import com.icet.onlinefoodordering.online_food_ordering.repository.CartRepository;
import com.icet.onlinefoodordering.online_food_ordering.repository.FoodRepository;
import com.icet.onlinefoodordering.online_food_ordering.request.AddCartItemRequest;
import com.icet.onlinefoodordering.online_food_ordering.service.CartService;
import com.icet.onlinefoodordering.online_food_ordering.service.FoodService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final FoodService foodService;

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Food food=foodService.findFoodById(req.getFoodId());
        Cart cart=cartRepository.findByCustomerId(user.getId());

        for(CartItem cartItem : cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(),newQuantity);
            }
        }
        CartItem newCartItem=new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem savedCartItem=cartItemRepository.save(newCartItem);
        
        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional= cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item=cartItemOptional.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice()*quantity);


        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFormCart(Long cartItemId, String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Cart cart=cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional= cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found");
        }
        CartItem item=cartItemOptional.get();
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

        Long total=0L;
        for(CartItem cartItem: cart.getItems()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }

        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart=cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("cart not found with id"+id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

        return cartRepository.findByCustomerId(userId);
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {
        Cart cart=findCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
