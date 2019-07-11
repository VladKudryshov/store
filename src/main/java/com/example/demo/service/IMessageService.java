package com.example.demo.service;

import com.example.demo.models.messages.Message;
import com.example.demo.models.messages.MessageDTO;

import java.util.List;
import java.util.Map;

public interface IMessageService {
    List<Message> getUserOrderMessages(Integer orderId);

    Map<Integer, List<Message>> getAllUserMessages();

    void sendMessageToUser(MessageDTO dto);
}
