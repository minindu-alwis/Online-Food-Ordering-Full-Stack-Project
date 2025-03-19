package com.icet.onlinefoodordering.online_food_ordering.controller;

import com.icet.onlinefoodordering.online_food_ordering.model.Cart;
import com.icet.onlinefoodordering.online_food_ordering.model.CartItem;
import com.icet.onlinefoodordering.online_food_ordering.model.User;
import com.icet.onlinefoodordering.online_food_ordering.request.AddCartItemRequest;
import com.icet.onlinefoodordering.online_food_ordering.request.UpdateCartItemRequest;
import com.icet.onlinefoodordering.online_food_ordering.service.CartService;
import com.icet.onlinefoodordering.online_food_ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem=cartService.addItemToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody UpdateCartItemRequest req,
                                                  @RequestHeader("Authorization") String jwt) throws Exception {
        CartItem cartItem=cartService.updateCartItemQuantity(req.getCartItemId(),req.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @DeleteMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id,
                                                           @RequestHeader("Authorization") String jwt) throws Exception {
        Cart cart = cartService.removeItemFormCart(id,jwt);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserByJwtToken(jwt);

        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }


}
