package com.oguzhan.food_delivery;

import com.oguzhan.food_delivery.entity.Role;
import com.oguzhan.food_delivery.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByAuthority("USER") == null) {

            Role userRole = new Role();
            userRole.setAuthority("USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByAuthority("ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setAuthority("ADMIN");
            roleRepository.save(adminRole);
        }
    }
}
