package com.example.demo.controllers;

import com.example.demo.models.messages.Message;
import com.example.demo.models.messages.MessageDTO;
import com.example.demo.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "api/messages")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    private void sendMessageToUser(@RequestBody MessageDTO dto) {
        messageService.sendMessageToUser(dto);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    private Map<Integer, List<Message>> getAllUserMessages() {
        return messageService.getAllUserMessages();
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.POST)
    private List<Message> getUserOrderMessages(@PathVariable Integer orderId) {
        return messageService.getUserOrderMessages(orderId);
    }
}
