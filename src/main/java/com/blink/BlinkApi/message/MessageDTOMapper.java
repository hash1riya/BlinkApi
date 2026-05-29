package com.blink.BlinkApi.message;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
class MessageDTOMapper implements Function<Message, MessageDTO> {
    @Override
    public MessageDTO apply(Message m) {
        return new MessageDTO(
                m.id,
                m.userId,
                m.content,
                m.timeStamp
        );
    }
}
