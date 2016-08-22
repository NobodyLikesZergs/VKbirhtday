package com.example.maq.sdr.domain.entities;

import java.util.Date;

public class Account {
    private Friend friend;
    private boolean isActive;
    private AccountType accountType;
    private Date lastInteraction;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Date getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(Date lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
