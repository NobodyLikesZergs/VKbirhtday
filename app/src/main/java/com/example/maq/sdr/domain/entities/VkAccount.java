package com.example.maq.sdr.domain.entities;

public class VkAccount extends Account{

    public VkAccount(String id, boolean isActive, String imgUrl,
                   String birthDate, String name) {
        super(AccountType.VK_ACCOUNT, id, isActive, imgUrl, birthDate, name);
    }

    public VkAccount(String id, boolean isActive, String imgUrl,
                     String birthDate, String firstName, String lastName) {
        super(AccountType.VK_ACCOUNT, id, isActive, imgUrl, birthDate, firstName + " " + lastName);
    }

}
