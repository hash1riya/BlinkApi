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

    @GetMapping
    public List<MessageDTO> findAll() { return this.service.findAll(); }

    @GetMapping("/{id}")
    public MessageDTO findById(@PathVariable String id) { return this.service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody MessageDTO req) { return this.service.create(req); }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable String id, @RequestParam String req)
    { return this.service.update(id, req); }

    @DeleteMapping("/{id}")
    public MessageDTO delete(@PathVariable String id) { return this.service.delete(id); }

}
