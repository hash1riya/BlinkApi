package com.blink.BlinkApi.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class MessageService {

    private final MessageRepository repo;

    public List<MessageDTO> findAll() {
        return this.repo.findAll()
                .stream()
                .map(MessageMapper::toDto)
                .collect(Collectors.toList());
    }

    public MessageDTO findById(String id) {
        return this.repo.findById(id)
                .map(MessageMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageService.class + ": Message not found"
        ));
    }

    public Message findEntityById(String id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MessageService.class + ": Message not found"
                ));
    }

    public MessageDTO create(MessageDTO msg) {
        Message newMsg = MessageMapper.toEntity(msg);
        return MessageMapper.toDto(this.repo.save(newMsg));
    }

    public MessageDTO update(String targetId, MessageDTO upd) {
        Message targetMsg = this.findEntityById(targetId);

        targetMsg.setContent(upd.content());
        targetMsg.setTimeStamp(ZonedDateTime.now());

        return MessageMapper.toDto(this.repo.save(targetMsg));
    }

    public MessageDTO delete(String id) {
        MessageDTO dltMsg = this.findById(id);
        this.repo.delete(this.findEntityById(id));
        return dltMsg;
    }
}
