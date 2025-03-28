package org.example.stomp;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notification {
    private String message;
    private String time;
}
