package com.blink.BlinkApi.room;

import com.blink.BlinkApi.user.UserDTO;
import com.blink.BlinkApi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/blink/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService rService;
    private final MembershipService mService;
    private final UserService uService;

    // -- Core Room Management --

    // 1. Get all rooms list
    @GetMapping
    public List<RoomDTO> findAll() { return this.rService.findAll(); }

    // 2. Find room by ID
    @GetMapping("/{id}")
    public RoomDTO findById(@PathVariable String id) { return this.rService.findById(id); }

    // 3. Create new room
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomDTO create(@RequestBody RoomDTO req) { return this.rService.create(req); }

    // 4. Update existing room
    @PutMapping("/{id}")
    public RoomDTO update(
            @PathVariable String id,
            @RequestBody RoomDTO req) {

        return this.rService.update(id, req);
    }

    // 5. Delete room
    @DeleteMapping("/{id}")
    public RoomDTO delete(@PathVariable String id) { return this.rService.delete(id); }



    // --- Membership Management ---

    // 1. Get all users per room
    @GetMapping("/{roomId}/members")
    public List<UserDTO> getAllUsersPerRoom(@PathVariable String roomId) {
        List<String> userIds = this.mService.findUsersPerRoom(roomId);
        return this.uService.findAllByIds(userIds);
    }

    // 2. Join room
    @PostMapping("/{roomId}/members")
    public boolean joinRoom(
            @PathVariable String roomId,
            @RequestParam String userId) {

        return this.mService.createMembership(roomId, userId);
    }

    // 3. Leave room
    @DeleteMapping("/{roomId}/members/{userId}")
    public boolean leaveRoom(
            @PathVariable String roomId,
            @PathVariable String userId) {

        Membership m = this.mService.findByRoomUserId(roomId, userId);

        if (m.getRole() == UserRole.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    RoomController.class
                    + ": You are Administrator"
            );
        }

        return this.mService.deleteMembership(m.getId());
    }

    // 4. Update user role
    @PatchMapping("/{roomId}/members/{userId}")
    public boolean updateUserRole(
            @PathVariable String roomId,
            @PathVariable String userId,
            @RequestParam UserRole role) {

        return this.mService.updateMembershipRole(roomId, userId, role);
    }

}
