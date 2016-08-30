package com.example.maq.sdr.data.remote;

import android.util.Log;

import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.presentation.MainApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource{

    private String mVkToken;

    private VkApiRetrofit mService;

    private final String mBaseUrl = "https://api.vk.com/method/";

    private static RemoteDataSourceImpl INSTANCE;

    private RemoteDataSourceImpl(String vkToken) {
        this.mService = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(VkApiRetrofit.class);
        mVkToken = vkToken;
    }

    public static RemoteDataSourceImpl getInstance(String vkToken) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSourceImpl(vkToken);
        else
            INSTANCE.setVkToken(vkToken);
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() {
        Call<ResponseBean> call = mService.getVkAccountsList(mVkToken);
        Response<ResponseBean> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }
        List<Friend> result = new ArrayList<>();
        Log.i(MainApplication.LOG_TAG, "remote loadFriends: " + response.raw().toString());
        for (VkAccountBean accountBean: response.body().getVkAccountBeanList()) {
            result.add(new Friend(accountBean.createVkAccountObject()));
        }
        return result;
    }

    @Override
    public Friend getFriend(int id) {
        return null;
    }

    @Override
    public void setVkToken(String vkToken) {
        mVkToken = vkToken;
    }
}
