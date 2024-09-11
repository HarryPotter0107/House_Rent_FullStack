package com.SmartBridge.HouseRent.repos;

import com.SmartBridge.HouseRent.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<UserModel, String> {
    UserModel findByEmail(String email);
}
