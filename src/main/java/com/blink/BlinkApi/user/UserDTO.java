package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

public record UserDTO(
        String id,

        String name,
        String email,
        String desc,
        UserStatus status,

        LocalDateTime createdAt
) {
}
