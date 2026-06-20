package com.oguzhan.food_delivery.controller.auth;

import com.oguzhan.food_delivery.dto.user.AuthResponseDto;
import com.oguzhan.food_delivery.dto.user.LoginRequestDto;
import com.oguzhan.food_delivery.dto.user.RegisterRequestDto;
import com.oguzhan.food_delivery.dto.user.UserResponseDTO;
import com.oguzhan.food_delivery.entity.User;
import com.oguzhan.food_delivery.security.CurrentUserService;
import com.oguzhan.food_delivery.security.JwtService;
import com.oguzhan.food_delivery.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(@RequestHeader("Authorization") String token) {
        User user = currentUserService.getCurrentUser();

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );

        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) throws Exception {
        return ResponseEntity.ok(authenticationService.register(registerRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return ResponseEntity.ok(authenticationService.login(loginRequestDto.email(), loginRequestDto.password()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok("Başarıyla çıkış yapıldı!");
    }
}
