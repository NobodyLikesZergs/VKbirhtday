package com.example.maq.sdr.domain.entities;

public abstract class Account {

    private String id;

    private AccountType accountType;

    private boolean isActive;

    private String imgUrl;

    private String name;

    private String birthDate;

    public Account(AccountType accountType, String id, boolean isActive, String imgUrl,
                   String birthDate, String name) {
        this.id = id;
        this.accountType = accountType;
        this.isActive = isActive;
        this.imgUrl = imgUrl;
        this.birthDate = birthDate;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
