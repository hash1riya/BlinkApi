package com.blink.BlinkApi.room;

import com.blink.BlinkApi.common.RoomMemberDTO;
import com.blink.BlinkApi.common.RoomMemberMapper;
import com.blink.BlinkApi.user.UserDTO;
import com.blink.BlinkApi.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public RoomDTO create(@RequestBody RoomDTO req) {

        RoomDTO newRoom = this.rService.create(req);

        this.mService.createMembership(
                newRoom.id(),
                newRoom.ownerId(),
                UserRole.OWNER
        );

        return newRoom;
    }

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
    public List<RoomMemberDTO> findAllUsersPerRoom(@PathVariable String roomId) {

        List<Membership> ms = this.mService.findMembershipsByRoomId(roomId);
        List<String> userIds = ms
                .stream()
                .map(Membership::getUserId)
                .toList();
        List<UserDTO> users = this.uService.findAllByIds(userIds);

        Map<String, Membership> membershipMap = ms
                .stream()
                .collect(Collectors.toMap(
                        Membership::getUserId,
                        m -> m));

        return users
                .stream()
                .map(u ->
                        RoomMemberMapper.toDto(
                                u,
                                membershipMap.get(u.id())))
                .toList();
    }

    // 2. Join room
    @PostMapping("/{roomId}/members")
    public RoomMemberDTO joinRoom(
            @PathVariable String roomId,
            @RequestParam String userId) {

        UserDTO u = this.uService.findById(userId);
        Membership m = this.mService.createMembership(roomId, userId);

        return RoomMemberMapper.toDto(u, m);
    }

    // 3. Leave room
    @DeleteMapping("/{roomId}/members/{userId}")
    public boolean leaveRoom(
            @PathVariable String roomId,
            @PathVariable String userId) {

        Membership m = this.mService.findByRoomUserId(roomId, userId);
        Room r = this.rService.findEntityById(m.getRoomId());

        if (r.getOwnerId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    RoomController.class
                            + ": User " + userId
                            + " is the Owner of the room " + roomId
            );
        }

        return this.mService.deleteMembership(m);
    }

    // 4. Update user role
    @PatchMapping("/{roomId}/members/{userId}")
    public RoomMemberDTO updateUserRole(
            @PathVariable String roomId,
            @PathVariable String userId,
            @RequestParam UserRole role) {

        UserDTO u = this.uService.findById(userId);
        Membership updM = this.mService.updateMembershipRole(roomId, userId, role);

        return RoomMemberMapper.toDto(u, updM);
    }

}
