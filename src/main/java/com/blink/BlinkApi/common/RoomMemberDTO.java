package com.blink.BlinkApi.common;

import com.blink.BlinkApi.room.UserRole;
import com.blink.BlinkApi.user.UserStatus;

import java.time.LocalDateTime;

public record RoomMemberDTO(
        String id,

        String name,
        String desc,

        UserStatus status,
        UserRole role,

        LocalDateTime joinedAt
) {

}
