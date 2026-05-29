package com.blink.BlinkApi.message;

import java.time.LocalDateTime;

public record MessageDTO(
        String id,
        String userId,
        String roomId,
        String content,
        LocalDateTime timeStamp,
        LocalDateTime lastUpdate
) {

}
