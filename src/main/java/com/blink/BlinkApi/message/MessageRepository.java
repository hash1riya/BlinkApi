package com.blink.BlinkApi.message;

import org.springframework.data.mongodb.repository.MongoRepository;

interface MessageRepository extends MongoRepository<Message, String> {

}
