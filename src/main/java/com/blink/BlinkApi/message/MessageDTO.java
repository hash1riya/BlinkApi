package com.blink.BlinkApi.message;

import java.time.LocalDateTime;

public record MessageDTO(
        String id,
        String userId,
        String roomId,

        String username,
        String content,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

}
