package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

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

    static User toEntity(UserDTO uDto) {
        return new User(
                uDto.id(),
                uDto.name(),
                uDto.desc(),
                uDto.email(),
                uDto.status(),
                uDto.createdAt()
        );
    }

    static User toFreshEntity(UserDTO uDto) {
        return new User(
                null,
                uDto.name(),
                uDto.email(),
                uDto.desc(),
                UserStatus.OFFLINE,
                LocalDateTime.now()
        );
    }
}
