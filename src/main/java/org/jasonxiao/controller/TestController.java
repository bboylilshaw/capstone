package org.jasonxiao.controller;

import org.jasonxiao.websocket.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;

/**
 * @author Jason Xiao
 */
@RestController
public class TestController {

    private final NotificationHandler handler;

    @Autowired
    public TestController(NotificationHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/message")
    public String receive(@RequestBody String msg) throws Exception {
        WebSocketMessage<String> socketMessage = new TextMessage(msg);
        handler.handleMessage(null, socketMessage);
        return "OK";
    }
}
