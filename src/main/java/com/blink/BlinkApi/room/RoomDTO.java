package com.blink.BlinkApi.room;

import java.time.LocalDateTime;

public record RoomDTO(
        String id,
        String ownerId,

        String name,
        String desc,

        RoomType type,

        LocalDateTime createdAt
) {

}
