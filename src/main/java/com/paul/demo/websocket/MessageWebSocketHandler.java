package com.paul.demo.websocket;

import com.paul.demo.dto.ChatMessage;
import com.paul.demo.dto.IChatMessage;
import com.paul.demo.entities.Message;
import com.paul.demo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.UUID;

@Controller
@Slf4j
public class MessageWebSocketHandler {

    private final MessageService messageService;

    public MessageWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Handles sending a chat message to all subscribers.
     *
     * @param chatMessage The chat message to be sent
     * @return The same chat message
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public IChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        log.info("Received request to send chat message: {}", chatMessage);

        // Generate unique ID for the message
        UUID uuid = UUID.randomUUID();
        Message message = new Message(uuid.toString(), chatMessage.getContent());

        // Save the message
        messageService.save(message);
        log.debug("Message saved: {}", message);

        return chatMessage;
    }

    /**
     * Handles adding a user to the chat.
     *
     * @param chatMessage    The chat message containing user information
     * @param headerAccessor The message header accessor
     * @return The same chat message
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public IChatMessage addUser1(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ){
        log.info("Received request to add user: {}", chatMessage);

        // Add username in web socket session
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", chatMessage.getSender());

        return chatMessage;
    }
}
