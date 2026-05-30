package com.blink.BlinkApi.room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RoomRepository extends MongoRepository<Room, String> {

}
