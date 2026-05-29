package com.blink.BlinkApi.room;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface RoomMapper {

    static RoomDTO toDto(Room r) {
        return new RoomDTO(
                r.getId(),
                r.getOwnerId(),
                r.getName(),
                r.getDesc(),
                r.getMembers(),
                r.getTimeStamp()
        );
    }

    static Room toFreshEntity(RoomDTO rDto) {
        return new Room(
                null,
                rDto.ownerId(),
                rDto.name(),
                rDto.desc(),
                rDto.members(),
                rDto.timeStamp() != null ? rDto.timeStamp() : LocalDateTime.now()
        );
    }
}
