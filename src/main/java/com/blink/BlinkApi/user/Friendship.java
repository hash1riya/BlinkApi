package com.blink.BlinkApi.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "friendships")
class Friendship {

    @Id
    private String id;

    private String requesterId;
    private String receiverId;

    private FriendshipStatus status;

    private LocalDateTime timeStamp;
}
