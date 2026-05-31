package com.blink.BlinkApi.message;

import java.time.LocalDateTime;

public interface MessageMapper {

    static MessageDTO toDto(Message m) {
        return new MessageDTO(
                m.getId(),
                m.getUserId(),
                m.getRoomId(),
                m.getContent(),
                m.getTimeStamp(),
                m.getLastUpdate()
        );
    }

    static Message toEntity(MessageDTO mDto) {
        return new Message(
                mDto.id(),
                mDto.userId(),
                mDto.roomId(),
                mDto.content(),
                mDto.timeStamp(),
                mDto.lastUpdate()
        );
    }

    static Message toFreshEntity(MessageDTO mDto) {
        return new Message(
                null,
                mDto.userId(),
                mDto.roomId(),
                mDto.content(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
