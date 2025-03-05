package com.sygec.sygec.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sygec.sygec.repository.AdminRepository;

@Component
public class DatabaseInitializer {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostConstruct
    public void init() {
        adminRepository.findAll().forEach(admin -> {
            if (admin.getMotDePass() == null || admin.getMotDePass().isEmpty()) {
                // Définir un mot de passe temporaire (ex: "123456")
                admin.setMotDePass(passwordEncoder.encode("123456"));
                adminRepository.save(admin);
                System.out.println("Mot de passe mis à jour pour: " + admin.getEmail());
            }
        });
    }
}