package com.afser.Ecom.service;

import com.afser.Ecom.dto.AddressDto;
import com.afser.Ecom.dto.UserResponse;
import com.afser.Ecom.model.UserModel;
import com.afser.Ecom.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public List<UserResponse> getAllUsers() {
        return repo.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public UserModel createUser(UserModel user) {
        System.out.println(user);
        System.out.println("Role = " + user.getRole());
    return repo.save(user);
    }

    private UserResponse mapToUserResponse(UserModel user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName((user.getFirstName()));
        response.setLastName((user.getLastName()));
        response.setEmail((user.getEmail()));
        response.setPhone((user.getPhone()));
        response.setRole((user.getRole()));

        if(user.getAddress() != null) {
            AddressDto dto= new AddressDto();
            dto.setStreet(user.getAddress().getStreet());
            dto.setCity(user.getAddress().getCity());
            dto.setState(user.getAddress().getState());
            dto.setCountry(user.getAddress().getCountry());
            dto.setZipCode(user.getAddress().getZipCode());
            response.setAddressDto(dto);
        }
        return response;
    }
}
