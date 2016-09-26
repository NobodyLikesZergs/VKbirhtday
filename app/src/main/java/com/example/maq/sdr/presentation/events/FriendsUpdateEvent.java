package com.example.maq.sdr.presentation.events;

public class FriendsUpdateEvent {

    private Result result;

    public FriendsUpdateEvent(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }


    public enum Result {
        OK,
        NOT_NEED,
        CONNECTION_ERROR
    }
}
