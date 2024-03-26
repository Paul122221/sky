package com.paul.demo.unit.entities;

import com.paul.demo.entities.Message;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    /**
     * Test to verify message creation with correct arguments.
     */
    @Test
    public void shouldCreateMessage_WhenCorrectTwoArgs(){
        // Arrange
        Message message;

        // Act
        message = new Message("id","text");

        // Assert
        assertNotEquals(message, null);
    }

    /**
     * Test to verify message creation with correct three arguments.
     */
    @Test
    public void shouldCreateMessage_WhenCorrectThreeArgs(){
        // Arrange
        Message message;

        // Act
        message = new Message("id","text", new Date());

        // Assert
        assertNotEquals(message, null);
    }

    /**
     * Test to verify exception is thrown when id is null.
     */
    @Test
    public void shouldThrowException_WhenIdIsNull(){
        // Assert & Act
        assertThrows(IllegalArgumentException.class, ()->{
            // Act
            Message message = new Message(null, "test");
        });
    }

    /**
     * Test to verify exception is thrown when text is empty.
     */
    @Test
    public void shouldThrowException_WhenTextIsEmpty(){
        // Assert & Act
        assertThrows(IllegalArgumentException.class, ()->{
            // Act
            Message message = new Message("id", "");
        });
    }

    // Todo: Add more tests to check the validity of the message, such as ensuring the ID is not empty, the creation date is not set in the future, and much more.
}
