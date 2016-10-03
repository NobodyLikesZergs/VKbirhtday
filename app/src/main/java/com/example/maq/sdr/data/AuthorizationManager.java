package com.example.maq.sdr.data;

public class AuthorizationManager {

    private String mVkToken;

    private static AuthorizationManager INSTANCE;

    private AuthorizationManager() {

    }

    public static AuthorizationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthorizationManager();
        }
        return INSTANCE;
    }

    public boolean isEmpty() {
        if (mVkToken == null)
            return true;
        return false;
    }

    public String getVkToken() {
        return mVkToken;
    }

    public void setVkToken(String vkToken) {
        mVkToken = vkToken;
    }
}
