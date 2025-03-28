package org.example.stomp;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private String message;
    private String sender;
    private LocalDateTime timestamp;
    private MessageType type;

    public enum MessageType {
        CHAT,JOIN,LEAVE
    }
}
