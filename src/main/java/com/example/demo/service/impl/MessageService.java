package com.example.demo.service.impl;

import com.example.demo.dao.IMessageDAO;
import com.example.demo.dao.IOrderContactDAO;
import com.example.demo.dao.IOrderDAO;
import com.example.demo.dao.IUserDAO;
import com.example.demo.dao.IUserInfoDAO;
import com.example.demo.exceptions.user.UserAlreadyExistException;
import com.example.demo.exceptions.user.UserNotAuthenticated;
import com.example.demo.exceptions.user.UserNotFoundException;
import com.example.demo.models.messages.Message;
import com.example.demo.models.messages.MessageDTO;
import com.example.demo.models.orders.Order;
import com.example.demo.models.orders.OrderContact;
import com.example.demo.models.user.UserEntity;
import com.example.demo.models.user.UserInfo;
import com.example.demo.service.IMessageService;
import com.example.demo.service.IOrderService;
import com.example.demo.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderDAO orderDAO;

    @Autowired
    private IMessageDAO messageDAO;

    public List<Message> getUserOrderMessages(MessageDTO dto) {
        String userId = userService.getAuthenticatedUser().getId();
        return messageDAO.findAllByFromUserAndToUserAndOrderId(userId,userId, dto.getOrderId());
    }

    @Override
    public List<Message> getUserOrderMessages(Integer orderId) {
        return null;
    }

    @Override
    public Map<Integer, List<Message>> getAllUserMessages() {
        String userId = userService.getAuthenticatedUser().getId();
        return messageDAO.findAllByFromUserAndToUser(userId, userId)
                .stream()
                .collect(Collectors.groupingBy(Message::getOrderId));
    }

    @Override
    public void sendMessageToUser(MessageDTO dto) {
        Integer orderId = dto.getOrderId();
        Order order = orderDAO.findOne(orderId);
        if(Objects.nonNull(order)){
            String toUserId = order.getUserId();
            String fromUserId = userService.getAuthenticatedUser().getId();
            Message msg = new Message();
            msg.setDate(new Date());
            msg.setToUser(toUserId);
            msg.setFromUser(fromUserId);
            msg.setOrderId(orderId);
            msg.setMessage(dto.getMessage());
            messageDAO.save(msg);
        }
    }
}
