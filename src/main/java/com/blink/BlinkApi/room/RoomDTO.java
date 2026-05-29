package com.blink.BlinkApi.room;

import java.time.LocalDateTime;
import java.util.List;

public record RoomDTO(
        String id,
        String ownerId,

        String name,
        String desc,

        List<String>members,

        LocalDateTime timeStamp
) {

}
