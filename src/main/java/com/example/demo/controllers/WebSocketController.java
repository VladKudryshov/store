package com.example.demo.controllers;

import com.example.demo.models.messages.Message;
import com.example.demo.models.messages.MessageDTO;
import com.example.demo.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class WebSocketController {

    @Autowired
    ChatHistoryDao chatHistoryDao;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private IMessageService messageService;

    @MessageMapping("/room/{room}/sendMessage")
    public MessageDTO post(@DestinationVariable String room, @Payload MessageDTO message) {

        template.convertAndSend("/topic/"+room, message);
        messageService.sendMessageToUser(message);

        return message;
    }

    @RequestMapping("/history/{id}")
    public List<Message> getChatHistory(@PathVariable Integer id) {
        return messageService.getUserOrderMessages(id);
    }

}