package com.blink.BlinkApi.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blink/messages")
public class MessageController {

    private final static Logger LOGGER = LoggerFactory. getLogger(MessageController.class);
    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping
    public List<MessageDTO> findAll() { return this.service.findAll(); }

    @GetMapping("/{id}")
    public MessageDTO findById(@PathVariable String id) { return this.service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody MessageDTO req) { return this.service.create(req); }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable String id, @RequestBody MessageDTO req) { return this.service.update(id, req);}

    @DeleteMapping("/{id}")
    public MessageDTO delete(@PathVariable String id) { return this.service.delete(id); }

}
