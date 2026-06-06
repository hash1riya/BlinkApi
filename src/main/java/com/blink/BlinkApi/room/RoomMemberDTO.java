package com.blink.BlinkApi.room;

import com.blink.BlinkApi.user.UserDTO;

import java.time.LocalDateTime;

public record RoomMemberDTO(
        String id,

        UserDTO user,

        MemberRole role,

        LocalDateTime joinedAt
) {

}
