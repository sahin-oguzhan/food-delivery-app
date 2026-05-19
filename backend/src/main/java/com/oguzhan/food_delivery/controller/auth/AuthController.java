package com.oguzhan.food_delivery.controller.auth;

import com.oguzhan.food_delivery.dto.user.AuthResponseDto;
import com.oguzhan.food_delivery.dto.user.LoginRequestDto;
import com.oguzhan.food_delivery.dto.user.RegisterRequestDto;
import com.oguzhan.food_delivery.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

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
