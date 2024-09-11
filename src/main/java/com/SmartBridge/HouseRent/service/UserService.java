package com.SmartBridge.HouseRent.service;

import com.SmartBridge.HouseRent.models.UserModel;
import com.SmartBridge.HouseRent.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public UserModel findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<UserModel> findById(String id) {
        return userRepo.findById(id);
    }

    public UserModel saveUser(UserModel user) {
        return userRepo.save(user);
    }
}
