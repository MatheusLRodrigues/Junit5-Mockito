package com.appsdeveloperMl.estore.service;

import com.appsdeveloperMl.estore.model.User;

public interface UserService {

    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);

}
