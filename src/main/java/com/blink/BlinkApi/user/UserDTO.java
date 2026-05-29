package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

public record UserDTO(
        String id,

        String name,
        String desc,
        UserStatus status,

        LocalDateTime timeStamp
) {
}
