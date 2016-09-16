package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

public class Friend {

    private String id;

    private String name;

    private DateTime birthDate;

    private String imgUrl;

    private List<Account> accountList;

    public Friend (String id, String name, DateTime birthDate, String imgUrl,
                   List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.imgUrl = imgUrl;
        this.accountList = accounts;
    }

    public Friend(Account account) {
        id = account.getId();
        name = account.getName();
        this.birthDate = account.getBirthDate();
        imgUrl = account.getImgUrl();
        accountList = new LinkedList<>();
        accountList.add(account);
    }

    public Friend(List<Account> accounts) {
        this(accounts.get(0));
        setAccountList(accounts);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }
}
