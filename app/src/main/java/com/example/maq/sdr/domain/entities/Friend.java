package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class Friend {
    private String id; //TODO resolve friend ID
    private String name;
    private DateTime birthDate;
    private String imgUrl;
    private DateTime lastInteractionDate;
    private VkAccount vkAccount;
    private String[] dateFormats = {"dd.MM.yyyy", "dd.MM"};
    private String currentDateFormat;

    public Friend(String id, String name, String birthDate, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        setBirthDate(birthDate);
    }

    private void setBirthDate(String birthDate) {
        if (birthDate == null) {
            this.birthDate = null;
            return;
        }
        for (String format: dateFormats) {
            try {
                this.birthDate = DateTimeFormat.forPattern(format).parseDateTime(birthDate);
                currentDateFormat = format;
                return;
            } catch (Exception e) {}
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        if (birthDate == null) {
            return "";
        }
        return birthDate.toString(DateTimeFormat.forPattern(currentDateFormat));
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
