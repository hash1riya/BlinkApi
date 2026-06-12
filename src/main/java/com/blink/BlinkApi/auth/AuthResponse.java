package com.blink.BlinkApi.auth;

public record AuthResponse(
        String token,
        String id,
        String username,
        String email
) {
}
