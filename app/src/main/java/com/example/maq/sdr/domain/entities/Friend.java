package com.example.maq.sdr.domain.entities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.LinkedList;
import java.util.List;

public class Friend {

    private String id;

    private String name;

    private DateTime birthDate;

    private String imgUrl;

    private List<Account> accountList;

    private String[] dateFormats = {"dd.MM.yyyy", "dd.MM"};

    private String currentDateFormat;

    public Friend (String id, String name, String birthDate, String imgUrl,
                   List<Account> accounts) {
        this.id = id;
        this.name = name;
        setBirthDate(birthDate);
        this.imgUrl = imgUrl;
        this.accountList = accounts;
    }

    public Friend(Account account) {
        id = account.getId();
        name = account.getName();
        setBirthDate(account.getBirthDate());
        imgUrl = account.getImgUrl();
        accountList = new LinkedList<>();
        accountList.add(account);
    }

    public Friend(List<Account> accounts) {
        this(accounts.get(0));
        setAccountList(accounts);
    }

    private void setBirthDate(String birthDate) {
        if (birthDate != null) {
            for (String format : dateFormats) {
                try {
                    this.birthDate = DateTimeFormat.forPattern(format).parseDateTime(birthDate);
                    currentDateFormat = format;
                    return;
                } catch (Exception e) {
                }
            }
        }
    }

    public String getBirthDate() {
        if (birthDate == null) {
            return "";
        }
        return birthDate.toString(DateTimeFormat.forPattern(currentDateFormat));
    }

    public long getDayOfMonth() {
        if (birthDate == null)
            return -1;
        return birthDate.getDayOfMonth();
    }

    public long getMonthOfYear() {
        if (birthDate == null)
            return -1;
        return birthDate.getMonthOfYear();
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

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
