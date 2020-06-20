package com.challenge2.core.java.mapper;

import com.challenge2.core.java.model.AppInput;

public class AppMapper {
    public static AppInput getInput(String[] fields) {
        AppInput appInput = new AppInput();
        appInput.setFirstName(fields[0]);
        appInput.setLastName(fields[1]);
        appInput.setEmail(fields[2]);
        appInput.setAmount(Integer.parseInt(fields[3]));
        appInput.setProductName(fields[4]);
        appInput.setBillNo(fields[5]);
        return appInput;
    }
}
