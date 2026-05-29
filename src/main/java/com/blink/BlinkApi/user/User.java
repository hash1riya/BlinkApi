package com.blink.BlinkApi.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    String id;

    String name;
    String desc;
    UserStatus status;

    List<User> friends;

    ZonedDateTime timeStamp;
}
