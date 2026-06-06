package com.blink.BlinkApi.auth;

import com.blink.BlinkApi.security.JwtService;
import com.blink.BlinkApi.user.User;
import com.blink.BlinkApi.user.UserRepository;
import com.blink.BlinkApi.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public AuthResponse register(RegisterRequest req) {

        User user = new User();
        user.setUsername(req.username());
        user.setEmail(req.email());

        user.setPassword(this.passwordEncoder.encode(req.password()));

        user.setDesc(req.desc());
        user.setStatus(UserStatus.OFFLINE);

        this.repo.save(user);

        String jwtToken = jwtService.generateToken(user.getUsername(), 1000 * 60 * 60 * 24);

        return new AuthResponse(
                jwtToken,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    public AuthResponse auth(AuthRequest req) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );

        User user = this.repo.findByUsername(req.username())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user.getUsername(), 1000 * 60 * 60 * 24);

        return new AuthResponse(
                jwtToken,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }



}
