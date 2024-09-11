package com.SmartBridge.HouseRent.service;

import com.SmartBridge.HouseRent.models.ApplicationModel;
import com.SmartBridge.HouseRent.repos.ApplicationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    ApplicationsRepo applicationsRepo;

    public List<ApplicationModel> findAll() {
        return applicationsRepo.findAll();
    }

    public Optional<ApplicationModel> findById(String id) {
        return applicationsRepo.findById(id);
    }

    public ApplicationModel saveApplication(ApplicationModel application) {
        return applicationsRepo.save(application);
    }

    public void deleteById(String id) {
        applicationsRepo.deleteById(id);
    }
}
