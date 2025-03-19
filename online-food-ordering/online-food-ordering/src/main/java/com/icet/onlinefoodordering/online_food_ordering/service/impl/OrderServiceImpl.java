package com.icet.onlinefoodordering.online_food_ordering.service.impl;

import com.icet.onlinefoodordering.online_food_ordering.model.*;
import com.icet.onlinefoodordering.online_food_ordering.repository.*;
import com.icet.onlinefoodordering.online_food_ordering.request.OrderRequest;
import com.icet.onlinefoodordering.online_food_ordering.service.CartService;
import com.icet.onlinefoodordering.online_food_ordering.service.OrderService;
import com.icet.onlinefoodordering.online_food_ordering.service.RestaurantService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;
    private  final RestaurantRepository restaurantRepository;
    private final CartService cartService;


    @Override
    public Order createOrder(OrderRequest order, User user) throws Exception {

        Address shippingAddress=order.getDeliveryAddress();
        Address savedAddress=addressRepository.save(shippingAddress);

        if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());

        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt((java.sql.Date) new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart=cartService.findCartByUserId(user.getId());

        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem cartItem: cart.getItems()){
            OrderItem orderItem=new OrderItem();
           orderItem.setFood(cartItem.getFood());
           orderItem.setIngredients(cartItem.getIngredients());
           orderItem.setQuantity(cartItem.getQuantity());
           orderItem.setTotalPrice(cartItem.getTotalPrice());

           OrderItem savedOrderItem=orderItemRepository.save(orderItem);
           orderItems.add(savedOrderItem);
        }

        Long totalPrice=cartService.calculateCartTotals(cart);

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder=orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);
        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {

        Order order=findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
        ){
            order.setOrderStatus(orderStatus);
            return orderRepository.save(order);
        }
        throw new Exception("Please Select A Valid Order Status");

    }

    @Override
    public void cancelOrder(Long orderId) throws Exception {
        Order oder=findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders=orders.stream().filter(order->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }

        return orders;

    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("order not found");
        }
        return optionalOrder.get();
    }
}
