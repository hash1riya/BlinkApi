package com.blink.BlinkApi.message;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class MessageService {

    private final MessageRepository repo;
    private final MessageDTOMapper mapper;

    public List<MessageDTO> findAll() {
        return this.repo.findAll()
                .stream()
                .map(this.mapper)
                .collect(Collectors.toList());
    }

    public MessageDTO findById(String id) {
        return this.repo.findById(id)
                .map(this.mapper)
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
        Message newMsg = new Message();

        newMsg.setUserId(msg.userId());
        newMsg.setContent(msg.content());
        newMsg.setTimeStamp(msg.timeStamp());

        return this.mapper.apply(this.repo.save(newMsg));
    }

    public MessageDTO update(String targetId, MessageDTO upd) {
        Message targetMsg = this.findEntityById(targetId);

        targetMsg.setContent(upd.content());
        targetMsg.setTimeStamp(upd.timeStamp());

        return this.mapper.apply(this.repo.save(targetMsg));
    }

    public MessageDTO delete(String id) {
        MessageDTO dltMsg = this.findById(id);
        this.repo.delete(this.findEntityById(id));
        return dltMsg;
    }
}
