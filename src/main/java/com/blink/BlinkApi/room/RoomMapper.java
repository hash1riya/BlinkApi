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

    static Room toEntity(RoomDTO rDto) {
        return new Room(
                rDto.id(),
                rDto.ownerId(),
                rDto.name(),
                rDto.desc(),
                rDto.createdAt()
        );
    }

    static Room toFreshEntity(RoomDTO rDto) {
        return new Room(
                null,
                rDto.ownerId(),
                rDto.name(),
                rDto.desc(),
                null
        );
    }
}
