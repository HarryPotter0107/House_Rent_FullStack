package com.SmartBridge.HouseRent.repos;

import com.SmartBridge.HouseRent.models.PropertiesModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertiesRepo extends MongoRepository<PropertiesModel, String> {
    PropertiesModel findByOwnerId(String ownerId);
}
