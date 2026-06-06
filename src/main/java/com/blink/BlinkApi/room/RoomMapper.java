package com.blink.BlinkApi.room;

interface RoomMapper {

    static RoomDTO toDto(Room r) {
        return new RoomDTO(
                r.getId(),
                r.getOwnerId(),
                r.getName(),
                r.getDesc(),
                r.getCreatedAt()
        );
    }
}
