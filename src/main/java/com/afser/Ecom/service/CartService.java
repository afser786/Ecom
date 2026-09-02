package com.afser.Ecom.service;

import com.afser.Ecom.dto.CartItemRequest;
import com.afser.Ecom.model.CartItem;
import com.afser.Ecom.model.Product;
import com.afser.Ecom.model.UserModel;
import com.afser.Ecom.repo.CartRepo;
import com.afser.Ecom.repo.ProductRepo;
import com.afser.Ecom.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    //@Autowired
    private final CartRepo repo;

    //@Autowired
    private final ProductRepo productRepo;

    //@Autowired
    private final UserRepo userRepo;

    CartService(CartRepo repo, ProductRepo productRepo, UserRepo userRepo) {
        this.repo = repo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public boolean addToCart(String userId, CartItemRequest request) {
       Optional<Product> productOpt=productRepo.findById(request.getProductId());

       if(productOpt.isEmpty())
           return false;

       Product product=productOpt.get();
           if(product.getStockQuantity()< request.getQuantity())
               return false;

           Optional<UserModel>userModel=userRepo.findById(Long.valueOf(userId));
        if(userModel.isEmpty())
            return false;

        UserModel user=userModel.get();
        CartItem existingCartItem=repo.findByUserAndProduct(user, product);
        if(existingCartItem!=null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            repo.save(existingCartItem);

        }else {
            CartItem cartItem=new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
           repo.save(cartItem);
       }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt=productRepo.findById(productId);
        if(productOpt.isEmpty())
            return false;

        Optional<UserModel>userModel=userRepo.findById(Long.valueOf(userId));
        if(userModel.isEmpty())
            return false;

        UserModel user = userModel.get();
        Product product = productOpt.get();

        repo.deleteByUserAndProduct(user, product);
        return true;
        //return false;
    }

    public List<CartItem> getCart(String userId) {
        return userRepo.findById(Long.valueOf(userId))
                .map(repo::findByUser)
                .orElseGet(List::of);
    }
}
