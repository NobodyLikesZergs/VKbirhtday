package com.example.maq.sdr.data.remote.beans;

import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.VkAccount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.LinkedList;

public class VkAccountBean {

    @SerializedName("user_id")
    @Expose
    private String id;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("photo_200_orig")
    @Expose
    private String imgUrl;

    @SerializedName("photo_100")
    @Expose
    private String photo100;

    @SerializedName("bdate")
    private String birthDate;

    public VkAccount createVkAccountObject() {
        DateTime parsedBirthDate = null;
        String[] formats = {"dd.MM.yyyy", "dd.MM"};
        for (int i = 0; i < 2; i++) {
            try {
                parsedBirthDate = DateTimeFormat.forPattern(formats[i]).parseDateTime(birthDate);
                if (i == 1) {
                    parsedBirthDate = parsedBirthDate.withYear(3000);
                }
            } catch (Exception e) {

            }
        }
        return new VkAccount(id, imgUrl, photo100, parsedBirthDate, new LinkedList<Message>(),
                firstName, lastName);
    }
}
