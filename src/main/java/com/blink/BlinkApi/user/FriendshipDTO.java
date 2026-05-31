package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

public record FriendshipDTO(
        String id,

        UserDTO friend,

        FriendshipStatus status,

        String actionUserId,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime lastInteractionAt
) {

}
