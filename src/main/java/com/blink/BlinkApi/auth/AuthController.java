package com.blink.BlinkApi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blink/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(
            @RequestBody RegisterRequest req
    ) {
        return this.service.register(req);
    }

    @PostMapping("auth")
    public AuthResponse auth(
            @RequestBody AuthRequest req
    ) {
        return this.service.auth(req);
    }

}
