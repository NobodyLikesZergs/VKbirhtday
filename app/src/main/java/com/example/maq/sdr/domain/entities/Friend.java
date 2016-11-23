package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

public class Friend {

    private String id;

    private String name;

    private DateTime birthDate;

    private String imgUrl;

    private String photo100;

    private List<Account> accountList;

    public Friend (String id, String name, DateTime birthDate, String imgUrl, String photo100,
                   List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.imgUrl = imgUrl;
        this.photo100 = photo100;
        this.accountList = accounts;
    }

    public Friend(Account account) {
        id = account.getId();
        name = account.getName();
        this.birthDate = account.getBirthDate();
        imgUrl = account.getImgUrl();
        this.photo100 = account.getPhoto100();
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Friend)) {
            return false;
        }
        Friend f = (Friend)o;
        return this.getId().equals(((Friend) o).getId());
    }

    public String getPhoto100() {
        return photo100;
    }

    public void setPhoto100(String photo100) {
        this.photo100 = photo100;
    }

    public boolean isUntuned() {
        for (Account account: this.getAccountList()) {
            if (!account.getMessageList().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
