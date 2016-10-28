package com.example.maq.sdr.presentation.tabs;

import android.content.Context;

public interface TabContract {

    interface View {

        Context getCurrentContext();

        void showConnectionErrorIcon();

        void hideConnectionErrorIcon();
    }

    interface Presenter {

        void onActivityStart();

        void onActivityStop();

    }

}
