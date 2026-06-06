package com.blink.BlinkApi.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public Membership findByRoomAndUserId(String roomId, String userId) {
        return this.repo
                .findByRoomUserId(roomId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MembershipService.class
                                + ": User " + userId
                                + " does not participate in room " + roomId
                ));
    }

    public List<Membership> findAllUsersByRoomId(String id) {
        return this.repo.findByRoomId(id);
    }

    public List<Membership> findAllRoomsByUserId(String userId) {
        return this.repo.findByUserId(userId);
    }

    public Membership createMembership(String roomId, String userId, UserRole role) {

        if (this.repo.findByRoomUserId(roomId, userId).isPresent())
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

        return this.repo.save(m);
    }

    public Membership createMembership(String roomId, String userId) {
        return this.createMembership(roomId, userId, UserRole.MEMBER);
    }

    public boolean updateMembershipRole(String roomId, String userId, UserRole r) {
        this.repo.updateRole(roomId, userId, r);
        return true;
    }

    public boolean deleteMembership(Membership m) {
        this.repo.delete(m);
        return true;
    }
}
