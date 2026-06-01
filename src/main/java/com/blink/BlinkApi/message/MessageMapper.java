package com.blink.BlinkApi.message;

public interface MessageMapper {

    static MessageDTO toDto(Message m) {
        return new MessageDTO(
                m.getId(),
                m.getUserId(),
                m.getRoomId(),
                m.getContent(),
                m.getCreatedAt(),
                m.getUpdatedAt()
        );
    }

    static Message toEntity(MessageDTO mDto) {
        return new Message(
                mDto.id(),
                mDto.userId(),
                mDto.roomId(),
                mDto.content(),
                mDto.createdAt(),
                mDto.updatedAt()
        );
    }

    static Message toFreshEntity(MessageDTO mDto) {
        return new Message(
                null,
                mDto.userId(),
                mDto.roomId(),
                mDto.content(),
                null,
                null
        );
    }
}
