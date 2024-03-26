package com.paul.demo.controller;

import com.paul.demo.entities.Message;
import com.paul.demo.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/messages")
@Slf4j
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Retrieves messages based on provided parameters.
     *
     * @param startDate     Start date for fetching messages
     * @param lastMessageId Last message ID for pagination
     * @param batchSize     Number of messages to fetch
     * @return ResponseEntity containing the list of messages
     * @throws ParseException if there's an error parsing the start date
     */
    @GetMapping("")
    public ResponseEntity<List<Message>> get(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String lastMessageId,
            @RequestParam(defaultValue = "20") int batchSize
    ) throws ParseException {
        log.info("Received request to fetch messages.");

        List<Message> messages;

        if (startDate != null && lastMessageId != null) {
            Date startDate1
                    = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                    .parse(startDate + "+00:00");
            messages = messageService
                    .getLastMessagesFrom(
                            startDate1,
                            lastMessageId,
                            Pageable.ofSize(batchSize)
                    );
            log.debug(
                    "Fetched messages based on start date {} and last message ID {}.",
                    startDate,
                    lastMessageId
            );
        } else {
            messages = messageService.getLastMessages(Pageable.ofSize(batchSize));
            log.debug("Fetched messages without specific criteria.");
        }

        log.info("Returning {} messages.", messages.size());
        return ResponseEntity.ok(messages);
    }
}
