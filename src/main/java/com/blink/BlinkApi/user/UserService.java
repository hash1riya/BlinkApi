package com.blink.BlinkApi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;


    public List<UserDTO> findAll() {
        return this.repo.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> findAllByIds(List<String> ids) {
        return this.repo.findAllById(ids)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }


    public UserDTO findById(String id) {
        return this.repo.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        UserService.class + ": User not found"
                ));
    }

    public UserDTO findByUsername(String username) {
        return this.repo.findByUsername(username)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        UserService.class
                                + ": User " + username
                                + "not found"
                ));
    }

    public UserDTO findByEmail(String email) {
        return this.repo.findByEmail(email)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        UserService.class
                                + ": User " + email
                                + "not found"
                ));
    }

    public User findEntityById(String id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        UserService.class + ": Room not found"
                ));
    }

    public UserDTO create(UserDTO user) {
        User newUser = UserMapper.toFreshEntity(user);
        return UserMapper.toDto(this.repo.save(newUser));
    }

    public UserDTO update(String targetId, UserDTO upd) {
        User targetUser = this.findEntityById(targetId);

        if (upd.name() != null)
            targetUser.setUsername(upd.name());
        if (upd.desc() != null)
            targetUser.setDesc(upd.desc());
        if (upd.status() != null)
            targetUser.setStatus(upd.status());

        return UserMapper.toDto(this.repo.save(targetUser));
    }

    public UserDTO delete(String id) {
        UserDTO dltUser = this.findById(id);
        this.repo.delete(this.findEntityById(id));
        return dltUser;
    }
}
