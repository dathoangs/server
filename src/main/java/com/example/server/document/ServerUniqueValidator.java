package com.example.server.document;

import com.example.server.model.Server;
import com.example.server.repo.ServerRepo;
import com.example.server.service.ServerService;
import com.example.server.service.implementation.ServerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ServerUniqueValidator implements ConstraintValidator<Unique,String> {

    @Autowired
    private ServerRepo serverRepo;

    @Override
    public void initialize(Unique unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext context) {
        if (serverRepo != null && serverRepo.findByIpAddress(ipAddress) != null) {
            return false;
        }
        return true;
    }
}