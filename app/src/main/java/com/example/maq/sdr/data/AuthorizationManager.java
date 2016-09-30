package com.example.maq.sdr.data;

public class AuthorizationManager {

    private String mVkToken;

    private static AuthorizationManager INSTANCE;

    private AuthorizationManager() {
        mVkToken = "";
    }

    public static AuthorizationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthorizationManager();
        }
        return INSTANCE;
    }

    public String getVkToken() {
        return mVkToken;
    }

    public void setVkToken(String vkToken) {
        mVkToken = vkToken;
    }
}
