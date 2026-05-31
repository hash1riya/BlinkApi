package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

public record FriendshipDTO(
        String id,

        UserDTO friend,

        FriendshipStatus status,

        LocalDateTime timeStamp
) {

}
