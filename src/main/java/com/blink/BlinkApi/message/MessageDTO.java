package com.blink.BlinkApi.message;

import java.time.LocalDate;

public record MessageDTO(
        String id,
        String userId,
        String content,
        LocalDate timeStamp) {
}
