package com.oguzhan.food_delivery.repository;

import com.oguzhan.food_delivery.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByAuthority(String authority);
}
