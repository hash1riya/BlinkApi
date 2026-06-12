package com.blink.BlinkApi.user;

public interface UserMapper {
    static UserDTO toDto(User u) {
        return new UserDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getDesc(),
                u.getStatus(),
                u.getCreatedAt()
        );
    }
}
