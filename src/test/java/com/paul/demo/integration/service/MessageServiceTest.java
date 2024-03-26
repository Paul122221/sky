package com.paul.demo.integration.service;

import com.paul.demo.entities.Message;
import com.paul.demo.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class MessageServiceTest {

    private final MessageService messageService;

    @Autowired
    public MessageServiceTest(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Executes before each test to clean up the database.
     */
    @BeforeEach
    public void before(){
        messageService.deleteAll();
    }

    /**
     * Tests whether the service returns the correct number of messages when trying to get the last 10 messages.
     */
    @Test
    public void shouldReturn10Messages_WhenTryToGetLast10Messages(){

        // Arrange
        List<Message> messages = new ArrayList<>();
        List<Message> lastMessages;
        int messageListSize = 20;
        int messageListSliceSize = 10;

        // Act
        for (int i = 0; i < messageListSize; i++) {
            messages.add(new Message("id" + i, "content " + i));
        }
        messageService.saveAll(messages);
        lastMessages = messageService.getLastMessages(Pageable.ofSize(messageListSliceSize));

        // Assert
        assertEquals(lastMessages.size(), messageListSliceSize);
    }


    /**
     * Tests whether the service returns the expected number of messages when filtered by date and ID.
     */
    @Test
    public void shouldReturnExpectedNumberOfMessages_WhenFilteredByDateAndId() {
        // Arrange
        int totalMessages = 10;
        int expectedMessagesAfterFilter = 5;
        LocalDateTime baseTime = LocalDateTime.parse(
                "2024-03-22T17:46:39.420",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        );
        List<Message> messages = IntStream.range(0, totalMessages)
                .mapToObj(i -> new Message(
                        "id" + i,
                        "content " + i,
                        Timestamp.valueOf(baseTime.plusSeconds(i))
                ))
                .collect(Collectors.toList());

        messageService.saveAll(messages);

        // Act
        LocalDateTime filterTime = baseTime.plusSeconds(5); // More understandable unit for test readers
        List<Message> filteredMessages = messageService.getLastMessagesFrom(
                Timestamp.valueOf(filterTime),
                "id2",
                Pageable.ofSize(totalMessages)
        );

        // Assert
        assertEquals(expectedMessagesAfterFilter, filteredMessages.size());
    }
}
