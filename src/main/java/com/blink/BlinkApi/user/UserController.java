package com.blink.BlinkApi.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blink/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService uService;
    private final FriendshipService fService;

    private List<FriendshipDTO> toDto(List<Friendship> fs, String userId) {
        Map<String, Friendship> friendshipMap = fs.stream().collect(Collectors.toMap(
                f ->
                        f.getRequesterId().equals(userId)
                                ? f.getReceiverId()
                                : f.getRequesterId(),
                f -> f
        ));

        return this.uService.findAllByIds(friendshipMap.keySet().stream().toList())
                .stream()
                .map(u ->
                        FriendshipMapper.toDto(
                                friendshipMap.get(u.id()),
                                u
                        ))
                .toList();
    }

    // --- Core User Management

    // 1. Find all users
    @GetMapping
    public List<UserDTO> findAll() { return this.uService.findAll(); }

    // 2. Find user by ID
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable String id) { return this.uService.findById(id); }

    // 3. Update existing user
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserDTO req) { return this.uService.update(id, req); }

    // 4. Delete user
    @DeleteMapping("/{id}")
    public UserDTO delete(@PathVariable String id) { return this.uService.delete(id); }



    // --- Friendship Management ---

    // 1. Find all accepted friends per user
    @GetMapping("/{userId}/friends/accepted")
    public List<FriendshipDTO> findAllFriends(@PathVariable String userId) {
        List<Friendship> fs = this.fService.findAllAcceptedFriendships(userId);
        return this.toDto(fs, userId);
    }

    // 2. Find all received pending requests
    @GetMapping("/{userId}/friends/pending/received")
    public List<FriendshipDTO> findAllPendingRequestsReceived(@PathVariable String userId) {
        List<Friendship> fs = this.fService.findAllPendingRequestsReceived(userId);
        return this.toDto(fs, userId);
    }

    // 3. Find all sent pending requests
    @GetMapping("/{userId}/friends/pending/sent")
    public List<FriendshipDTO> findAllPendingRequestsSent(@PathVariable String userId) {
        List<Friendship> fs = this.fService.findAllPendingRequestsSent(userId);
        return this.toDto(fs, userId);
    }

    // 4. Send friend request
    @PostMapping("/{requesterId}/friends")
    public FriendshipDTO sendFriendRequest(
            @PathVariable String requesterId,
            @RequestParam String receiverId) {

        Friendship f = this.fService.createFriendship(requesterId, receiverId);
        return FriendshipMapper.toDto(f, this.uService.findById(receiverId));
    }

    // 5. Accept friend request
    @PatchMapping("/{receiverId}/friends")
    public boolean acceptFriendRequest(
            @PathVariable String receiverId,
            @RequestParam String requesterId) {

        return this.fService.updateFriendshipStatus(
                requesterId,
                receiverId,
                FriendshipStatus.ACCEPTED
        );
    }

    // 6. Delete friend or deny friend request
    @DeleteMapping("/{userA}/friends")
    public boolean deleteOrDenyFriend(
            @PathVariable String userA,
            @RequestParam String userB) {

        Friendship f = this.fService.findFriendshipBetween(userA, userB);
        return this.fService.deleteFriendship(f);
    }



    // --- User Search ---

    // 1. Search by username
    @GetMapping("/search/{username}")
    public UserDTO findByUsername(@PathVariable String username) {
        return this.uService.findByUsername(username);
    }

    // 2. Search by email
    @GetMapping("/search/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return this.uService.findByEmail(email);
    }

}