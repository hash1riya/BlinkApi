package com.blink.BlinkApi.message;

import java.time.ZonedDateTime;

public record MessageDTO(
        String id,
        String userId,
        String roomId,
        String content,
        ZonedDateTime timeStamp
) {

}
