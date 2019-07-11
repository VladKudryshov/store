package com.example.demo.dao;

import com.example.demo.models.messages.Message;
import com.example.demo.models.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMessageDAO extends JpaRepository<Message, Integer> {
    List<Message> findAllByFromUserAndToUserAndOrderId(String fromUserId, String toUserId, Integer orderId);

    List<Message> findAllByFromUserAndToUser(String fromUserId, String toUserId);
}
