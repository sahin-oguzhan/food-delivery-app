package com.oguzhan.food_delivery;

import com.oguzhan.food_delivery.entity.Product;
import com.oguzhan.food_delivery.entity.Restaurant;
import com.oguzhan.food_delivery.entity.Role;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.entity.Category;
import com.oguzhan.food_delivery.repository.ProductRepository;
import com.oguzhan.food_delivery.repository.RestaurantRepository;
import com.oguzhan.food_delivery.repository.RoleRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import com.oguzhan.food_delivery.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Role customerRole = roleRepository.findByAuthority("ROLE_CUSTOMER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setAuthority("ROLE_CUSTOMER");
                    return roleRepository.save(role);
                });

        Role adminRole = roleRepository.findByAuthority("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setAuthority("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        Role ownerRole = roleRepository.findByAuthority("ROLE_OWNER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setAuthority("ROLE_OWNER");
                    return roleRepository.save(role);
                });


        if (restaurantRepository.count() > 1) {
            System.out.println("Kayıt mevcut");
            return;
        }

        System.out.println("Kayıt başlıyor");

        User owner1 = new User();
        owner1.setUsername("ahmet_owner");
        owner1.setFirstName("Ahmet");
        owner1.setLastName("Yılmaz");
        owner1.setEmail("ahmet_owner@test.com");
        owner1.setPassword(passwordEncoder.encode("123456"));

        Set<Role> owner1Roles = new HashSet<>();
        owner1Roles.add(ownerRole);
        owner1.setRoles(owner1Roles);

        userRepository.save(owner1);


        User owner2 = new User();
        owner2.setUsername("zeynep_owner");
        owner2.setFirstName("Zeynep");
        owner2.setLastName("Kaya");
        owner2.setEmail("zeynep_owner@test.com");
        owner2.setPassword(passwordEncoder.encode("123456"));

        Set<Role> owner2Roles = new HashSet<>();
        owner2Roles.add(ownerRole);
        owner2.setRoles(owner2Roles);

        userRepository.save(owner2);

        Restaurant rest1 = new Restaurant();
        rest1.setName("Gaziantep Lahmacun & Kebap Dünyası");
        rest1.setDescription("Gerçek taş fırından odun ateşinde enfes lahmacunlar.");
        rest1.setAddress("Alsancak, İzmir");
        rest1.setPhoneNumber("02321112233");
        rest1.setOwner(owner1);
        restaurantRepository.save(rest1);

        Restaurant rest2 = new Restaurant();
        rest2.setName("Pizza & Pasta Bella");
        rest2.setDescription("İtalyan şeflerin elinden çıkan el yapımı makarnalar ve pizzalar.");
        rest2.setAddress("Karşıyaka, İzmir");
        rest2.setPhoneNumber("02324445566");
        rest2.setOwner(owner2);
       restaurantRepository.save(rest2);

        Category kebapCategory = new Category();
        kebapCategory.setName("Kebap");
        kebapCategory.setRestaurant(rest1);
        categoryRepository.save(kebapCategory);

        Category pizzaCategory = new Category();
        pizzaCategory.setName("Pizza");
        pizzaCategory.setRestaurant(rest2);
        categoryRepository.save(pizzaCategory);

        Product p1 = new Product();
        p1.setName("Çıtır Lahmacun");
        p1.setDescription("Bol malzemeli, çıtır çıtır Antep lahmacunu (Yeşillik ve limon ile)");
        p1.setPrice(BigDecimal.valueOf(75.0));
        p1.setRestaurant(rest1);
        p1.setCategory(kebapCategory);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Adana Dürüm");
        p2.setDescription("Zırh kıyması, közlenmiş biber ve domates ile.");
        p2.setPrice(BigDecimal.valueOf(180.0));
        p2.setRestaurant(rest1);
        p2.setCategory(kebapCategory);
        productRepository.save(p2);

        Product p3 = new Product();
        p3.setName("Pizza Margherita");
        p3.setDescription("Özel domates sosu, taze mozzarella peyniri ve fesleğen.");
        p3.setPrice(BigDecimal.valueOf(220.0));
        p3.setRestaurant(rest2);
        p3.setCategory(pizzaCategory);
        productRepository.save(p3);

        Product p4 = new Product();
        p4.setName("Fettuccine Alfredo");
        p4.setDescription("Kremalı sos, mantar ve ızgara tavuk dilimleri ile.");
        p4.setPrice(BigDecimal.valueOf(195.0));
        p4.setRestaurant(rest2);
        p4.setCategory(pizzaCategory);
        productRepository.save(p4);

        System.out.println("Tüm veriler işlendi");
    }
}