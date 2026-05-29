package com.blink.BlinkApi.message;

import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public interface MessageMapper {

    static MessageDTO toDto(Message m) {
        return new MessageDTO(
                m.getId(),
                m.getUserId(),
                m.getRoomId(),
                m.getContent(),
                m.getTimeStamp()
        );
    }

    static Message toEntity(MessageDTO mDto) {
        return new Message(
                null,
                mDto.userId(),
                mDto.roomId(),
                mDto.content(),
                ZonedDateTime.now()
        );
    }
}
