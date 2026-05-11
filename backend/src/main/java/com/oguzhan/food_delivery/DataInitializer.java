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
        if (roleRepository.findByAuthority("ROLE_CUSTOMER").isEmpty()) {

            Role userRole = new Role();
            userRole.setAuthority("ROLE_CUSTOMER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByAuthority("ROLE_ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setAuthority("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByAuthority("ROLE_OWNER").isEmpty()) {
            Role ownerRole = new Role();
            ownerRole.setAuthority("ROLE_OWNER");
            roleRepository.save(ownerRole);
        }
    }
}
