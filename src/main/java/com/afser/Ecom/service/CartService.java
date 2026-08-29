package com.afser.Ecom.service;

import com.afser.Ecom.dto.CartItemRequest;
import com.afser.Ecom.model.CartItem;
import com.afser.Ecom.repo.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepo repo;

    public void addToCart(String userId, CartItemRequest request) {
        repo.save(new CartItem());
    }
}
