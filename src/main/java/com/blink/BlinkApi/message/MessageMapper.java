package com.blink.BlinkApi.message;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
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
