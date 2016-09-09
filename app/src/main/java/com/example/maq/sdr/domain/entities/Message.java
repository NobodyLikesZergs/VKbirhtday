package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Message {

    private String id;
    private String text;
    private Account account;
    private DateTime date;
    private Friend friend;
    private String[] dateFormats = {"dd.MM.yyyy", "dd.MM"};

    public Message (String text, Account account, String date, Friend friend) {
        this.text = text;
        this.account = account;
        this.friend = friend;
        setDate(date);
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

    private void setDate(String date) {
        if (date != null) {
            for (String format : dateFormats) {
                try {
                    this.date = DateTimeFormat.forPattern(format).parseDateTime(date);
                    this.date = new DateTime(0, this.date.getMonthOfYear(),
                            this.date.getDayOfMonth(), 0, 0);
                    return;
                } catch (Exception e) {
                }
            }
        }
    }

    public String getDate() {
        if (this.date == null) {
            return "";
        }
        return this.date.toString(DateTimeFormat.forPattern(dateFormats[1]));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
