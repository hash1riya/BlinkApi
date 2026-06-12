package com.blink.BlinkApi.room;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface RoomRepository extends MongoRepository<Room, String> {

    @Query("{ 'name': '?0' }")
    Optional<Room> findByName(String name);
}
