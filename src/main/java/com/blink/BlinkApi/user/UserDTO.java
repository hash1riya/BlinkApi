package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

public record UserDTO(
        String id,

        String username,
        String email,
        String desc,
        String password,

        UserStatus status,

        LocalDateTime createdAt
) {
}
