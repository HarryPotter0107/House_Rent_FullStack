package com.SmartBridge.HouseRent.repos;

import com.SmartBridge.HouseRent.models.ApplicationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationsRepo extends MongoRepository<ApplicationModel, String> {
    ApplicationModel findByApplicantId(String applicantId);
}
