package com.afser.Ecom.service;

import com.afser.Ecom.dto.AddressDto;
import com.afser.Ecom.dto.OrderItemDTO;
import com.afser.Ecom.dto.OrderResponse;
import com.afser.Ecom.model.*;
import com.afser.Ecom.repo.OrderRepo;
import com.afser.Ecom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final UserRepo userRepo;

    public OrderService(OrderRepo orderRepo, CartService cartService, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.cartService = cartService;
        this.userRepo = userRepo;
    }

    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItem>cartItems=cartService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }
        Optional<UserModel> user=userRepo.findById(Long.valueOf(userId));
        if(user.isEmpty()){
            return Optional.empty();
        }
        UserModel userModel=user.get();
        BigDecimal totalPrice=cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order= new Order();
        order.setUser(userModel);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);
        List<OrderItem>orderItems=cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        order,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());
        order.setItems(orderItems);
        Order savedOrder=orderRepo.save(order);

        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(item -> new OrderItemDTO(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getPrice(),
                                item.getQuantity(),
                                item.getPrice().multiply(new BigDecimal(item.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }
}
