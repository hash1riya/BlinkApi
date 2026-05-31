package com.blink.BlinkApi.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class MembershipService {

    private final MembershipRepository repo;

    private Membership findById(String id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MembershipService.class
                                + ": This membership does not exist. ID: "
                                + id
                ));
    }

    public List<String> findUsersPerRoom(String roomId) {
        return this.repo
                .findByRoomId(roomId)
                .stream()
                .map(Membership::getUserId)
                .collect(Collectors.toList());
    }

    public Membership findByRoomUserId(String roomId, String userId) {
        return this.repo
                .findByRoomUserId(roomId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MembershipService.class
                                + ": User " + userId
                                + " not found in room " + roomId
                ));
    }

    public boolean createMembership(String roomId, String userId, UserRole role) {

        if (this.repo.findByRoomUserId(roomId, userId).isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    MembershipService.class
                    + ": User" + userId
                    + " already participates in room " + roomId
            );

        Membership m = new Membership();

        m.setRoomId(roomId);
        m.setUserId(userId);
        m.setRole(role);
        m.setJoinedAt(LocalDateTime.now());

        this.repo.save(m);

        return true;
    }

    public boolean createMembership(String roomId, String userId) {
        return this.createMembership(roomId, userId, UserRole.MEMBER);
    }

    public boolean deleteMembership(Membership m) {
        this.repo.delete(m);
        return true;
    }

    public boolean updateMembershipRole(String roomId, String userId, UserRole r) {
        Membership targetM = this.findByRoomUserId(roomId, userId);
        targetM.setRole(r);
        this.repo.save(targetM);
        return true;
    }
}
