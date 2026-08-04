package com.afser.Ecom.service;

import com.afser.Ecom.model.UserModel;
import com.afser.Ecom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepo repo;
    
    public List<UserModel> getAllUsers() {
        return repo.findAll();
    }

    public UserModel createUser(UserModel user) {
        System.out.println(user);
        System.out.println("Role = " + user.getRole());
    return repo.save(user);
    }
}
