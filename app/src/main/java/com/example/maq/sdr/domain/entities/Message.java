package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;

public class Message {

    private String id;
    private String text;
    private DateTime date;

    public Message (String id, String text, DateTime date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
