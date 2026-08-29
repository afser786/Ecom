package com.afser.Ecom.controller;

import com.afser.Ecom.dto.UserResponse;
import com.afser.Ecom.model.UserModel;
import com.afser.Ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers(){
    return service.getAllUsers();
}
    @PostMapping("/users")
    public UserModel createUser(@RequestBody UserModel user){
        return service.createUser(user);
}
}
