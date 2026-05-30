package com.blink.BlinkApi.room;

import java.time.LocalDateTime;

interface RoomMapper {

    static RoomDTO toDto(Room r) {
        return new RoomDTO(
                r.getId(),
                r.getOwnerId(),
                r.getName(),
                r.getDesc(),
                r.getTimeStamp()
        );
    }

    static Room toFreshEntity(RoomDTO rDto) {
        return new Room(
                null,
                rDto.ownerId(),
                rDto.name(),
                rDto.desc(),
                rDto.timeStamp() != null ? rDto.timeStamp() : LocalDateTime.now()
        );
    }
}
