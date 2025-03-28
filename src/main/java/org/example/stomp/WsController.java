package org.example.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WsController {
    @MessageMapping("/noti.read")
    @SendTo("/topic/public")
    public void addUser(Notification msg) {
        System.out.println("Received Msg: "+msg);
    }

    private int count = 0;
    private static final int MAX = 20;
    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 3000)
    public void sendNotification() {
        if(count >= MAX) {
            System.out.println("end sendNotification");
            return;
        }

        Notification noti = new Notification("send Notification", java.time.LocalDateTime.now().toString());
        messagingTemplate.convertAndSend("/topic/public", noti);
        count++;
    }
}
