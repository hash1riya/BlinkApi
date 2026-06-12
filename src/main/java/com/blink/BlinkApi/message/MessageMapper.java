package com.blink.BlinkApi.message;

public interface MessageMapper {

    static MessageDTO toDto(Message m) {
        return new MessageDTO(
                m.getId(),
                m.getUserId(),
                m.getRoomId(),
                m.getUsername(),
                m.getContent(),
                m.getCreatedAt(),
                m.getUpdatedAt()
        );
    }
}
