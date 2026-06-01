package com.blink.BlinkApi.common;

import com.blink.BlinkApi.room.Membership;
import com.blink.BlinkApi.user.UserDTO;

public interface RoomMemberMapper {

    static RoomMemberDTO toDto(UserDTO user, Membership m) {
        return new RoomMemberDTO(
                m.getId(),
                user,
                user.status(),
                m.getRole(),
                m.getJoinedAt()
        );
    }
}
