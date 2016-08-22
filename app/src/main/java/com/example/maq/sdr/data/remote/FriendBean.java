package com.example.maq.sdr.data.remote;

import com.example.maq.sdr.domain.entities.Friend;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendBean {

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
    private String bdate;

    public String getImgUrl() {
        return imgUrl;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Friend createFriendObject() {
        return new Friend(id, firstName + " " + lastName, bdate, imgUrl);
    }
}
