package com.example.maq.sdr.data.remote.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountResponseBean {

    @SerializedName("response")
    @Expose
    private List<VkAccountBean> VkAccountBeanList;

    @SerializedName("error")
    @Expose
    private ErrorBean errorBean;

    public ErrorBean getErrorBean() {
        return errorBean;
    }

    public List<VkAccountBean> getVkAccountBeanList() {
        return VkAccountBeanList;
    }
}
