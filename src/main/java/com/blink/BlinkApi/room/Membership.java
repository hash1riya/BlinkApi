package com.blink.BlinkApi.room;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "room_members")
class Membership {
    @Id
    private String id;
    private String roomId;
    private String userId;

    private UserRole role;

    private LocalDateTime joinedAt;
}
