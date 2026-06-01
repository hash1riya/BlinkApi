package com.blink.BlinkApi.auth;

public record AuthRequest(
        String username,
        String password
) {
}
