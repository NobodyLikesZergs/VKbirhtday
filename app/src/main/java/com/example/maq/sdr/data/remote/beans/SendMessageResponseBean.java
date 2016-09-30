package com.example.maq.sdr.data.remote.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMessageResponseBean {

    @SerializedName("error")
    @Expose
    private ErrorBean errorBean;

    public ErrorBean getErrorBean() {
        return errorBean;
    }
}
