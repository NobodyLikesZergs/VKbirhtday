package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;

public class Message {
    private String text;
    private Account account;
    private DateTime date;
    private Friend friend;

    public Message (String text, Account account, DateTime date, Friend friend) {
        this.text = text;
        this.account = account;
        this.date = date;
        this.friend = friend;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDateTime(DateTime date) {
        this.date = date;
    }
}
