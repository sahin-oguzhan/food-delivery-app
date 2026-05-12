package com.oguzhan.food_delivery.service;

import com.oguzhan.food_delivery.dto.user.AuthResponseDto;
import com.oguzhan.food_delivery.dto.user.RegisterRequestDto;
import com.oguzhan.food_delivery.entity.Role;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.repository.RoleRepository;
import com.oguzhan.food_delivery.repository.UserRepository;
import com.oguzhan.food_delivery.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto registerRequestDto) throws Exception {
        Optional<User> user = userRepository.findByUsernameOrEmail(
                registerRequestDto.username(), registerRequestDto.email());

        if (user.isPresent()) {
            User existUser = user.get();

            if (existUser.getUsername().equals(registerRequestDto.username()) && existUser.getEmail().equals(registerRequestDto.email())) {
                throw new Exception("Username and Email are already taken!");
            } else if (existUser.getUsername().equals(registerRequestDto.username())) {
                throw new Exception("Username is already taken!");
            } else if (existUser.getEmail().equals(registerRequestDto.email())) {
                throw new Exception("Email is already taken!");
            }
        }

        String encodedPassword = passwordEncoder.encode(registerRequestDto.password());
        String roleName = registerRequestDto.isOwner() ? "ROLE_OWNER" : "ROLE_CUSTOMER";
        Role userRole = roleRepository.findByAuthority(roleName)
                .orElseThrow(() -> new RuntimeException("Veritabanında " + roleName + " rolü bulunamadı!"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        User newUser = new User();
        newUser.setUsername(registerRequestDto.username());
        newUser.setEmail(registerRequestDto.email());
        newUser.setPassword(encodedPassword);
        newUser.setFirstName(registerRequestDto.firstName());
        newUser.setLastName(registerRequestDto.lastName());
        newUser.setRoles(roles);

        userRepository.save(newUser);

        var jwtToken = jwtService.generateToken(newUser);
        return new AuthResponseDto(jwtToken);

    }

    public AuthResponseDto login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var user = userRepository.findByEmail(email).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }
}
