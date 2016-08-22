package com.example.maq.sdr.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBean {

    @SerializedName("response")
    @Expose
    private List<FriendBean> friendBeanList;

    public List<FriendBean> getFriendBeanList() {
        return friendBeanList;
    }
}
