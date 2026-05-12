package com.oguzhan.food_delivery.controller.authentication;

import com.oguzhan.food_delivery.dto.user.AuthResponseDto;
import com.oguzhan.food_delivery.dto.user.LoginRequestDto;
import com.oguzhan.food_delivery.dto.user.RegisterRequestDto;
import com.oguzhan.food_delivery.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
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
}
