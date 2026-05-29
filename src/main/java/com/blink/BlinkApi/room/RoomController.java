package com.blink.BlinkApi.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blink/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @GetMapping
    public List<RoomDTO> findAll() { return this.service.findAll(); }

    @GetMapping("/{id}")
    public RoomDTO findById(@PathVariable String id) { return this.service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDTO create(@RequestBody RoomDTO req) { return this.service.create(req); }

    @PutMapping("/{id}")
    public RoomDTO update(@PathVariable String id, @RequestBody RoomDTO req) { return this.service.update(id, req); }

    @DeleteMapping("/{id}")
    public RoomDTO delete(@PathVariable String id) { return this.service.delete(id); }

}
