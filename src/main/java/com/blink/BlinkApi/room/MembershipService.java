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
                .map(Membership::getRoomId)
                .collect(Collectors.toList());
    }

    public Membership findByRoomUserId(String roomId, String userId) {
        return this.repo
                .findByRoomUserId(roomId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        MembershipService.class
                                + ": User " + userId
                                + " not found in group " + roomId
                ));
    }
    public boolean createMembership(String roomId, String userId) {
        Membership m = new Membership();

        m.setRoomId(roomId);
        m.setUserId(userId);
        m.setRole(UserRole.MEMBER);
        m.setJoinedAt(LocalDateTime.now());

        this.repo.save(m);
        return true;
    }

    public boolean deleteMembership(String mId) {
        this.repo.delete(this.findById(mId));
        return true;
    }

    public boolean updateMembershipRole(String roomId, String userId, UserRole r) {
        Membership targetM = this.findByRoomUserId(roomId, userId);
        targetM.setRole(r);
        return true;
    }
}
