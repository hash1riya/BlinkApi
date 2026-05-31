package com.blink.BlinkApi.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blink/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    // --- Core Message Management ---

    // 1. Get all messages
    @GetMapping
    public List<MessageDTO> findAll() { return this.service.findAll(); }

    // 2. Get message by ID
    @GetMapping("/{id}")
    public MessageDTO findById(@PathVariable String id) { return this.service.findById(id); }

    // 3. Create message
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody MessageDTO req) { return this.service.create(req); }

    // 4. Update message
    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable String id, @RequestParam String req) {
        return this.service.update(id, req);
    }

    // 5. Delete message
    @DeleteMapping("/{id}")
    public MessageDTO delete(@PathVariable String id) { return this.service.delete(id); }


    // --- Custom search ---

    // 1. Find all messages by content
    @GetMapping()
    public List<MessageDTO> findAllByContent(@RequestParam String content) {
        return this.service.findByContent(content);
    }

}
