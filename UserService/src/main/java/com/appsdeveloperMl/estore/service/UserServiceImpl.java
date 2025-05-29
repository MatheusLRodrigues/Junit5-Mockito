package com.appsdeveloperMl.estore.service;

import com.appsdeveloperMl.estore.data.UserRepository;
import com.appsdeveloperMl.estore.data.UserRepositoryImpl;
import com.appsdeveloperMl.estore.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    EmailVerificationService emailVerificationService;


    public UserServiceImpl(UserRepository userRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("User's first name is empty");

        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("User's last name is empty");

        }
        int isLess = 7;
        if (password == null || password.length() < isLess) {
            throw new IllegalArgumentException("User's password is less than 7");
        }

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());

        boolean isUserCreated;

        try {
            isUserCreated = userRepository.save(user);
        } catch (RuntimeException ex){
            throw new UserServiceException(ex.getMessage());
        }

        if(!isUserCreated) throw new UserServiceException("Could not create user");

        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }
        return user;
    }

    public void demoMethod(){
        System.out.println("Demo Method");
    }

}
