package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Message {

    private String id;
    private String text;
    private DateTime birthDate;
    private String[] dateFormats = {"dd.MM.yyyy", "dd.MM"};
    private String currentDateFormat;

    public Message (String id, String text, String date) {
        this.id = id;
        this.text = text;
        setBirthDate(date);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private void setBirthDate(String birthDate) {
        if (birthDate != null) {
            for (String format : dateFormats) {
                try {
                    this.birthDate = DateTimeFormat.forPattern(format).parseDateTime(birthDate);
                    currentDateFormat = format;
                    return;
                } catch (Exception e) {
                }
            }
        }
    }

    public String getBirthDate() {
        if (birthDate == null) {
            return "";
        }
        return birthDate.toString(DateTimeFormat.forPattern(currentDateFormat));
    }

    public long getDayOfMonth() {
        if (birthDate == null)
            return -1;
        return birthDate.getDayOfMonth();
    }

    public long getMonthOfYear() {
        if (birthDate == null)
            return -1;
        return birthDate.getMonthOfYear();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
