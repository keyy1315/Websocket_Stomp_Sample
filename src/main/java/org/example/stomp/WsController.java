package org.example.stomp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @MessageMapping("/chat.sendMsg")
    @SendTo("/topic/public")
    public Message sendMsg(Message msg) {
        System.out.println(msg);
        return msg;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(Message msg) {
        System.out.println(msg);
        msg.setMessage(msg.getSender() + " JOIN");
        msg.setType(Message.MessageType.JOIN);
        return msg;
    }
}
