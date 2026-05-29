package com.blink.BlinkApi.room;

import org.springframework.data.mongodb.repository.MongoRepository;

interface RoomRepository extends MongoRepository<Room, String> {

}
