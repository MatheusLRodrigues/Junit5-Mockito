package com.appsdeveloperMl.estore.service;

import com.appsdeveloperMl.estore.data.UserRepository;
import com.appsdeveloperMl.estore.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
//If it is used the PER_CLASS the share the same class instance so it will show 1234 instead 1 | 2 | 3 | 4 per method.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    EmailVerificationServiceImpl emailVerificationService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach
    void init() {
        firstName = "Sergey";
        lastName = "Kargopoloy";
        email = "test@test.com";
        password = "1234567";
        repeatPassword = "1234567";
    }

    @Order(1)
    @DisplayName("User object created")
    @Test
    void testCreateUser_whenUserDetailsProvided_returnUserObject() {
        //Arrange
        when(userRepository.save(any(User.class))).thenReturn(true);
        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);
        //Assert
        assertNotNull(user, "The createUser() should not have returned Null");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        assertNotNull(user.getId(), "User id is missing");
        verify(userRepository).save(Mockito.any(User.class));
    }

    @Order(2)
    @DisplayName("Empty first name causes correct exception")
    @Test
    void testCreateUser_whenFirstNameIsEmpty_throwsIllegalArgumentException() {
        //Arrange

        firstName = "";
        String expectedExceptionMessage = "User's first name is empty";
        //Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");
        //Assert
        assertEquals("User's first name is empty", thrown.getMessage(), "Exception message is not correct");

    }

    @Order(3)
    @DisplayName("Empty last name causes correct exception")
    @Test
    void testCreateUser_whenLastNameIsEmpty_throwsIllegalArgumentException() {
        //Arrange
        String lastName = "";
        String expectedExceptionMessage = "User's last name is empty";
        //Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty last name should have caused an Illegal Argument Exception");
        //Assert
        assertEquals("User's last name is empty", thrown.getMessage(), "Exception message is not correct");
    }

    @Order(4)
    @DisplayName("Password is shorter than 7 characters")
    @Test
    void testCreateUser_whenPasswordIsShorterThan7_throwsIllegalArgumentException() {
        //Arrange
        String password = "123456";
        String expectedExceptionMessage = "User's password is less than 7";
        //Act & Assert
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Password should have cause an Illegal Argument Exception");
        //Assert
        assertEquals("User's password is less than 7", thrown.getMessage(), "Exception message is not correct");
    }

    @Order(5)
    @DisplayName("Save method")
    @Test
    void testCreateUser_whenSaveMethodThrowsException_thenThrowsUserServiceException(){
        //Arrange
        when(userRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        //Act
        assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException");
        //Assert

    }

    @Order(6)
    @DisplayName("EmailNotificationException is handled")
    @Test
    void testCreateUser_whenEmailNotificationExceptionThrown_throwsUserServiceException(){
        //Arrange
        when(userRepository.save(any(User.class))).thenReturn(true);

        doThrow(EmailNotificationServiceException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
        //Act & Assert
        assertThrows(UserServiceException.class, ()-> {
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Should have thrown UserServiceException instead");

        // Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    @DisplayName("Schedule Email Confirmation is executed")
    @Test
    void testCreateUser_whenUserCreated_schedulesEmailConfirmation(){
        //Arrange
        when(userRepository.save(any(User.class))).thenReturn(true);

        doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        //Act
        userService.createUser(firstName,lastName,email,password,repeatPassword);

        //Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }
}
