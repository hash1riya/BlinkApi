package com.blink.BlinkApi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class FriendshipService {

    private final FriendshipRepository repo;

    private Friendship findById(String id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        FriendshipService.class
                                + ": This friendship does not exist. ID: "
                                + id
                ));
    }

    public Friendship findFriendshipBetween(String userA, String userB) {
        return this.repo.findFriendshipBetween(userA, userB)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        FriendshipService.class
                        + ": User " + userA
                        + "is not a friend to user " + userB
                ));
    }

    public List<Friendship> findAllRequestsPerUser(String userId) {
        return this.repo.findAllRequestsPerUser(userId);
    }

    public List<Friendship> findAllAcceptedFriendships(String userId) {
        return this.repo.findAllAcceptedFriendships(userId);
    }

    public List<Friendship> findAllPendingRequestsReceived(String userId) {
        return this.repo.findAllPendingRequestsReceived(userId);
    }

    public List<Friendship> findAllPendingRequestsSent(String userId) {
        return this.repo.findAllPendingRequestsSent(userId);
    }

    public Friendship createFriendship(String requesterId, String receiverId) {

        if (this.repo.findFriendshipBetween(requesterId, receiverId).isPresent())
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    FriendshipService.class + ": This friendship already exists"
            );

        Friendship f = new Friendship();

        f.setRequesterId(requesterId);
        f.setReceiverId(receiverId);
        f.setStatus(FriendshipStatus.PENDING);
        f.setTimeStamp(LocalDateTime.now());

        return this.repo.save(f);
    }

    public boolean updateFriendshipStatus(
            String userA,
            String userB,
            FriendshipStatus f) {

        this.repo.updateStatus(userA, userB, f);
        return true;
    }

    public boolean deleteMembership(Friendship f) {
        this.repo.delete(f);
        return true;
    }

}
