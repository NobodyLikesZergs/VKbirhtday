package com.example.maq.sdr.presentation.authorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.maq.sdr.presentation.MainApplication;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        VKSdk.login(this, VKScope.FRIENDS, VKScope.MESSAGES);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                ((MainApplication) getApplication()).updateVkToken();
                Log.i("Vk onResult token:", res.accessToken);
            }

            @Override
            public void onError(VKError error) {
                Log.e("Vk error", "onError");
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
