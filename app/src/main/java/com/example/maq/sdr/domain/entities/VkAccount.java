package com.example.maq.sdr.domain.entities;

import java.util.List;

public class VkAccount extends Account{

    public VkAccount(String id, String imgUrl, String birthDate,
                     List<Message> messages,  String name) {
        super(AccountType.VK_ACCOUNT, id, imgUrl, birthDate, messages, name);
    }

    public VkAccount(String id, String imgUrl, String birthDate,
                     List<Message> messages, String firstName, String lastName) {
        super(AccountType.VK_ACCOUNT, id, imgUrl, birthDate, messages, firstName + " " + lastName);
    }

}
