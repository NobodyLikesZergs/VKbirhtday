package com.example.maq.sdr.data.remote;

import android.util.Log;

import com.example.maq.sdr.data.remote.beans.AccountResponseBean;
import com.example.maq.sdr.data.remote.beans.SendMessageResponseBean;
import com.example.maq.sdr.data.remote.beans.VkAccountBean;
import com.example.maq.sdr.domain.entities.Account;
import com.example.maq.sdr.domain.entities.Friend;
import com.example.maq.sdr.domain.entities.Message;
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

    private RemoteDataSourceImpl() {
        this.mService = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(VkApiRetrofit.class);
    }

    public static RemoteDataSourceImpl getInstance(String vkToken) {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSourceImpl();
        INSTANCE.setVkToken(vkToken);
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() {
        Call<AccountResponseBean> call = mService.getVkAccountsList(mVkToken);
        Response<AccountResponseBean> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return null;
        }
        List<Friend> result = new ArrayList<>();
        for (VkAccountBean accountBean: response.body().getVkAccountBeanList()) {
//            TODO: remove mock logic
            Account account = accountBean.createVkAccountObject();
            account.addMessage(new Message(account.getId(),
                    account.getName() + " " + account.getBirthDate(), account.getBirthDate()));
            result.add(new Friend(account));
//            result.add(new Friend(accountBean.createVkAccountObject()));
        }
        return result;
    }

    @Override
    public Friend getFriend(int id) {
        return null;
    }

    @Override
    public void sendMessage(Account account, Message message) {
//        TODO: remove mock logic
        Call<SendMessageResponseBean> call = mService.sendMessage("42341262", message.getText(),
                mVkToken);
//        Call<SendMessageResponseBean> call = mService.sendMessage(account.getId(),
//                message.getText(), mVkToken);
        Response<SendMessageResponseBean> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            return;
        }
        Log.i(MainApplication.LOG_TAG, "sendMessage response: " + response.raw().toString());
    }

    @Override
    public void setVkToken(String vkToken) {
        mVkToken = vkToken;
    }
}
