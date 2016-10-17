package com.example.maq.sdr.data.remote;

import android.util.Log;

import com.example.maq.sdr.data.AuthorizationManager;
import com.example.maq.sdr.data.remote.beans.AccountResponseBean;
import com.example.maq.sdr.data.remote.beans.ErrorBean;
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

public class RemoteDataSourceImpl implements RemoteDataSource {

    private VkApiRetrofit mService;

    private final String mBaseUrl = "https://api.vk.com/method/";

    private AuthorizationManager authManager;

    private static RemoteDataSourceImpl INSTANCE;

    private RemoteDataSourceImpl() {
        this.mService = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(VkApiRetrofit.class);
        authManager = AuthorizationManager.getInstance();
    }

    public static RemoteDataSourceImpl getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSourceImpl();
        return INSTANCE;
    }

    @Override
    public List<Friend> getFriends() throws IOException, WrongTokenException {
        Call<AccountResponseBean> call = mService.getVkAccountsList(authManager.getVkToken());
        Response<AccountResponseBean> response = call.execute();
        List<Friend> result = new ArrayList<>();
        Log.i(MainApplication.LOG_TAG, "getFriends response: " + response.raw().toString());
        if(response.body().getErrorBean() != null) {
            parseError(response.body().getErrorBean());
        }
        for (VkAccountBean accountBean: response.body().getVkAccountBeanList()) {
            result.add(new Friend(accountBean.createVkAccountObject()));
        }
        return result;
    }

    @Override
    public void sendMessage(Account account, Message message) {
//        TODO: remove mock logic
        Call<SendMessageResponseBean> call = mService.sendMessage("42341262", message.getText(),
                authManager.getVkToken());
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

    private void parseError(ErrorBean errorBean) throws IOException, WrongTokenException {
        if (errorBean.getErrorCode() == 5 ||
                errorBean.getErrorCode() == 113) {
            throw new WrongTokenException();
        } else {
            throw new IOException();
        }
    }
}
