package com.example.maq.sdr.data.remote.beans;

import com.example.maq.sdr.domain.entities.Message;
import com.example.maq.sdr.domain.entities.VkAccount;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("bdate")
    private String birthDate;

    public VkAccount createVkAccountObject() {
        return new VkAccount(id, imgUrl, birthDate, new LinkedList<Message>(),
                firstName, lastName);
    }
}