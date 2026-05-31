package com.blink.BlinkApi.user;

interface FriendshipMapper {
    static FriendshipDTO toDto(
            Friendship f,
            UserDTO friend) {

        return new FriendshipDTO(
                f.getId(),
                friend,
                f.getStatus(),
                f.getActionUserId(),
                f.getCreatedAt(),
                f.getUpdatedAt(),
                f.getLastInteractionAt()
        );
    }
}
