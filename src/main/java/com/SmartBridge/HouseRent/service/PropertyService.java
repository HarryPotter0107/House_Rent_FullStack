package com.SmartBridge.HouseRent.service;

import com.SmartBridge.HouseRent.models.PropertiesModel;
import com.SmartBridge.HouseRent.repos.PropertiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    PropertiesRepo propertiesRepo;

    public List<PropertiesModel> findAll() {
        return propertiesRepo.findAll();
    }

    public Optional<PropertiesModel> findById(String id) {
        return propertiesRepo.findById(id);
    }

    public PropertiesModel saveProperty(PropertiesModel property) {
        return propertiesRepo.save(property);
    }
}
