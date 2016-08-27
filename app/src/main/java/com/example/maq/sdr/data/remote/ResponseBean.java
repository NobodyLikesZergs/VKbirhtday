package com.example.maq.sdr.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBean {

    @SerializedName("response")
    @Expose
    private List<VkAccountBean> VkAccountBeanList;

    public List<VkAccountBean> getVkAccountBeanList() {
        return VkAccountBeanList;
    }
}
