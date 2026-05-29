package com.blink.BlinkApi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blink/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDTO> findAll() { return this.service.findAll(); }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) { return this.service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO req) { return this.service.create(req); }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserDTO req) { return this.service.update(id, req); }

    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable String id) { return this.service.delete(id); }

}