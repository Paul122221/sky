package com.paul.demo.entities;

import java.util.Date;

public interface IMessage {
    String getId();

    void setId(String id);

    String getText();

    void setText(String text);

    Date getCreationDate();

    void setCreationDate(Date creationDate);
}
