package com.blink.BlinkApi.room;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class RoomService {

    private final RoomRepository repo;

    public List<RoomDTO> findAll() {
        return this.repo.findAll()
                .stream()
                .map(RoomMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoomDTO findById(String id) {
        return this.repo.findById(id)
                .map(RoomMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        RoomService.class
                                + ": Room " + id
                                + " not found"
                ));
    }

    public List<RoomDTO> findAllByIds(List<String> ids) {
        return this.repo.findAllById(ids)
                .stream()
                .map(RoomMapper::toDto)
                .toList();
    }

    public RoomDTO findByName(String name) {
        return this.repo.findByName(name)
                .map(RoomMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        RoomService.class
                                + ": Room " + name
                                + " not found"
                ));
    }

    public Room findEntityById(String id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        RoomService.class
                                + ": Room " + id
                                + " not found"
                ));
    }

    public RoomDTO create(RoomDTO room) {
        Room newRoom = new Room(
                null,
                room.ownerId(),
                room.name(),
                room.desc(),
                room.type(),
                null
        );
        return RoomMapper.toDto(this.repo.save(newRoom));
    }

    public RoomDTO update(String targetId, RoomDTO upd) {
        Room targetRoom = this.findEntityById(targetId);

        if (targetRoom.getName() != null)
            targetRoom.setName(upd.name());
        if (targetRoom.getDesc() != null)
            targetRoom.setDesc(upd.desc());

        return RoomMapper.toDto(this.repo.save(targetRoom));
    }

    public RoomDTO changeRoomOwner(String targetId, String newOwnerId) {
        Room targetRoom = this.findEntityById(targetId);
        targetRoom.setOwnerId(newOwnerId);
        return RoomMapper.toDto(this.repo.save(targetRoom));
    }

    public RoomDTO delete(String id) {
        RoomDTO dltRoom = this.findById(id);
        this.repo.delete(this.findEntityById(id));
        return dltRoom;
    }
}
