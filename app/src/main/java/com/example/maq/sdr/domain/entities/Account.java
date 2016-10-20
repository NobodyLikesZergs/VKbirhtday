package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

public abstract class Account {

    private String id;

    private AccountType accountType;

    private String imgUrl;

    private String photo100;

    private String name;

    private DateTime birthDate;

    private List<Message> messageList;

    public Account(AccountType accountType, String id, String imgUrl, String photo100,
                   DateTime birthDate, List<Message> messageList, String name) {
        this.id = id;
        this.accountType = accountType;
        this.imgUrl = imgUrl;
        this.photo100 = photo100;
        this.birthDate = birthDate;
        this.name = name;
        if (messageList == null) {
            this.messageList = new LinkedList<>();
        } else {
            this.messageList = messageList;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoto100() {
        return photo100;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }
}
