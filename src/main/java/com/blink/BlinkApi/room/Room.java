package com.blink.BlinkApi.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String ownerId;

    private String name;
    private String desc;

    private List<String> members;

    private LocalDateTime timeStamp;
}
