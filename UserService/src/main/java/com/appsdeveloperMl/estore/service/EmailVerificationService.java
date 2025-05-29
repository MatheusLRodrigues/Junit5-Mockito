package com.appsdeveloperMl.estore.service;

import com.appsdeveloperMl.estore.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
