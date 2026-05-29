package com.blink.BlinkApi.user;


import java.time.LocalDateTime;

public interface UserMapper {
    static UserDTO toDto(User u) {
        return new UserDTO(
                u.getId(),
                u.getUsername(),
                u.getDesc(),
                u.getStatus(),
                u.getTimeStamp()
        );
    }

    static User toFreshEntity(UserDTO uDto) {
        return new User(
                null,
                uDto.name(),
                uDto.desc(),
                uDto.status() != null ? uDto.status() : UserStatus.OFFLINE,
                uDto.timeStamp() != null ? uDto.timeStamp() : LocalDateTime.now()
        );
    }
}
