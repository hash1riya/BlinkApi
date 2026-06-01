package com.blink.BlinkApi.room;

import com.blink.BlinkApi.user.UserDTO;
import com.blink.BlinkApi.user.UserStatus;

import java.time.LocalDateTime;

public record RoomMemberDTO(
        String id,

        UserDTO user,

        UserStatus status,
        UserRole role,

        LocalDateTime joinedAt
) {

}
