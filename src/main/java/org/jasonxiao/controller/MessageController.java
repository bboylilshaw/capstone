package org.jasonxiao.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Jason Xiao
 */
@Controller
public class MessageController {

    @MessageMapping("/greetings")
    @SendTo("/topic/another")
    public String handle(String name) {
        System.out.println("handle greeting in controller");
        return "What up, " + name;
    }
}
