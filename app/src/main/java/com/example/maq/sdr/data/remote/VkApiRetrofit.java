package com.example.maq.sdr.data.remote;

import com.example.maq.sdr.data.remote.beans.AccountResponseBean;
import com.example.maq.sdr.data.remote.beans.SendMessageResponseBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VkApiRetrofit {

    @GET("friends.get?fields=photo_200_orig,bdate")
    Call<AccountResponseBean> getVkAccountsList(@Query("access_token") String token);

    @GET("messages.send?")
    Call<SendMessageResponseBean> sendMessage(@Query("user_id") String id,
                                              @Query("message") String text,
                                              @Query("access_token") String token);
}
