package com.blink.BlinkApi.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface MessageRepository extends MongoRepository<Message, String> {

    @Query("{ 'roomId': '?0' }")
    List<MessageDTO> findAllByRoomId(String roomId);

    @Query("{ 'content': { '$regex': '?0' } }")
    List<MessageDTO> findAllByContent(String content);
}