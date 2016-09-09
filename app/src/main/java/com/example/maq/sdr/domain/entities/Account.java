package com.example.maq.sdr.domain.entities;

import java.util.List;

public abstract class Account {

    private String id;

    private AccountType accountType;

    private String imgUrl;

    private String name;

    private String birthDate;

    private List<Message> messageList;

    public Account(AccountType accountType, String id, String imgUrl,
                   String birthDate, List<Message> messageList, String name) {
        this.id = id;
        this.accountType = accountType;
        this.imgUrl = imgUrl;
        this.birthDate = birthDate;
        this.name = name;
        this.messageList = messageList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
