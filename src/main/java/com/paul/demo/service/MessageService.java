package com.paul.demo.service;

import com.paul.demo.entities.Message;
import com.paul.demo.repositories.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service class for managing messages.
 */
@Service
@Slf4j
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Retrieves the last messages.
     *
     * @param pageable pagination information
     * @return List of last messages
     */
    public List<Message> getLastMessages(Pageable pageable){
        log.info("Retrieving last messages.");
        List<Message> messages = messageRepository.findTopNByOrderByCreationDateDescIdDesc(pageable);
        log.debug("Retrieved {} last messages.", messages.size());
        return messages;
    }

    /**
     * Retrieves the last messages from a specific start date and message ID.
     *
     * @param startDate    start date for fetching messages
     * @param minIdValue   minimum message ID value for pagination
     * @param pageable     pagination information
     * @return List of last messages from the specified start date and message ID
     */
    public List<Message> getLastMessagesFrom(Date startDate, String minIdValue, Pageable pageable){
        log.info("Retrieving last messages from {} with minIdValue: {}", startDate, minIdValue);
        List<Message> messages = messageRepository.getLastMessagesFrom(startDate, minIdValue, pageable);
        log.debug("Retrieved {} last messages from {} with minIdValue: {}", messages.size(), startDate, minIdValue);
        return messages;
    }

    /**
     * Saves a message.
     *
     * @param message the message to save
     * @return the saved message
     */
    public Message save(Message message){
        log.info("Saving message: {}", message);
        Message savedMessage = messageRepository.save(message);
        log.debug("Message saved: {}", savedMessage);
        return savedMessage;
    }

    /**
     * Saves multiple messages.
     *
     * @param messages the list of messages to save
     * @return the list of saved messages
     */
    public List<Message> saveAll(List<Message> messages){
        log.info("Saving multiple messages.");
        List<Message> savedMessages = messageRepository.saveAll(messages);
        log.debug("Multiple messages saved.");
        return savedMessages;
    }

    /**
     * Deletes all messages.
     */
    public void deleteAll(){
        log.info("Deleting all messages.");
        messageRepository.deleteAll();
        log.debug("All messages deleted.");
    }
}
