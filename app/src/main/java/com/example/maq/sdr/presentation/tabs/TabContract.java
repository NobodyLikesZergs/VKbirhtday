package com.example.maq.sdr.presentation.tabs;

public interface TabContract {

    interface View {

        void showConnectionErrorIcon();

        void hideConnectionErrorIcon();

        void showAuthorizationDialog();

        void showConnectionErrorDialog();
    }

    interface Presenter {

        void onActivityStart();

        void onActivityStop();

    }

}
