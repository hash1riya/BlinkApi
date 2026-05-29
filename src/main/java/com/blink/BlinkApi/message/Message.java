package com.blink.BlinkApi.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
class Message {
    @Id
    String id;
    String userId;
    String content;
    LocalDate timeStamp;
}
