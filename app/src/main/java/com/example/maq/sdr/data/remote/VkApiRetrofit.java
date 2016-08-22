package com.example.maq.sdr.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VkApiRetrofit {

    @GET("friends.get?fields=photo_200_orig")
    Call<ResponseBean> getFriendsList(@Query("access_token") String token);
}
