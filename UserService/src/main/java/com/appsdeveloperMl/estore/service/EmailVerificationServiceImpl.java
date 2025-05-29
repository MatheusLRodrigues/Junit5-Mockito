package com.appsdeveloperMl.estore.service;

import com.appsdeveloperMl.estore.model.User;

import java.sql.SQLOutput;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override
    public void scheduleEmailConfirmation(User user) {
        System.out.println("scheduledEmailConfirmation is called");
    }
}
