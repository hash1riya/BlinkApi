package com.blink.BlinkApi.auth;

public record RegisterRequest(
        String username,
        String email,
        String password,
        String desc
) {
}
