package com.paul.demo.dto;

public interface IChatMessage {

    MessageType getType();

    void setType(MessageType type);

    String getContent();

    void setContent(String content);

    String getSender();

    void setSender(String sender);
}
