package com.paul.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "messages")
public class Message implements IMessage {

    @Id
    private String id;

    private String text;

    @Indexed
    private Date creationDate;

    public Message() {
        id = UUID.randomUUID().toString();
        text = "empty message";
        creationDate = new Date();
    }

    public Message(String id, String text) {
        this.id = id;
        this.text = text;
        this.creationDate = new Date();
        validate();
    }

    public Message(String id, String text, Date creationDate) {
        this.id = id;
        this.text = text;
        this.creationDate = creationDate;
        validate(); // Call the validation method
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        validate(); // Call the validation method
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        validate(); // Call the validation method
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        validate(); // Call the validation method
    }

    /**
     * Validates the Message object.
     * Checks if the ID is not empty, the text is not empty,
     * and the creation date is not set in the future.
     * Throws IllegalArgumentException if any validation fails.
     */
    private void validate() {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID should not be null or empty");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text should not be null or empty");
        }
        if (creationDate == null || creationDate.after(new Date())) {
            throw new IllegalArgumentException("Creation date should not be null or in the future");
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id) &&
                text.equals(message.text) &&
                creationDate.equals(message.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, creationDate);
    }
}
