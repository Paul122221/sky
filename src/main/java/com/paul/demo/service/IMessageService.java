package com.paul.demo.service;

import com.paul.demo.entities.Message;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IMessageService {
    public List<Message> getLastMessages(Pageable pageable);
    public List<Message> getLastMessagesFrom(
            Date startDate,
            String minIdValue,
            Pageable pageable
    );
    public Message save(Message message);
}
