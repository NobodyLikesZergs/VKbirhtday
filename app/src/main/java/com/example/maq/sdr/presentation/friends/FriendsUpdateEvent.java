package com.example.maq.sdr.presentation.friends;

public class FriendsUpdateEvent {

    private boolean successful;

    public FriendsUpdateEvent(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
