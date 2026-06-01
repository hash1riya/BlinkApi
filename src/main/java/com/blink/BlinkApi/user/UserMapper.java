package com.blink.BlinkApi.user;

import java.time.LocalDateTime;

public interface UserMapper {
    static UserDTO toDto(User u) {
        return new UserDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getDesc(),
                u.getPassword(),
                u.getStatus(),
                u.getCreatedAt()
        );
    }

    static User toEntity(UserDTO uDto) {
        return new User(
                uDto.id(),
                uDto.username(),
                uDto.desc(),
                uDto.email(),
                uDto.password(),
                uDto.status(),
                uDto.createdAt()
        );
    }

    static User toFreshEntity(UserDTO uDto) {
        return new User(
                null,
                uDto.username(),
                uDto.email(),
                uDto.desc(),
                uDto.password(),
                UserStatus.OFFLINE,
                LocalDateTime.now()
        );
    }
}
