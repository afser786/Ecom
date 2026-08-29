package com.afser.Ecom.repo;

import com.afser.Ecom.model.CartItem;
import com.afser.Ecom.model.Product;
import com.afser.Ecom.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(UserModel user, Product product);

    void deleteByUserAndProduct(UserModel user, Product product);
}
