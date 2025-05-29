package com.matheuslrodrigues;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD) //If it is used the PER_CLASS the share the same class instance so it will show 1234 instead 1 | 2 | 3 | 4 per method.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderedByOrderIndexTest {

    StringBuilder completed = new StringBuilder("");

    @AfterEach
    void afterEach(){
        System.out.println("The state of instance object is: " + completed);
    }

    @Order(1)
    @Test
    void testB() {
        System.out.println("Running test B");
        completed.append("1");
    }

    @Order(2)
    @Test
    void testA() {
        System.out.println("Running test A");
        completed.append("2");
    }

    @Order(3)
    @Test
    void testD() {
        System.out.println("Running test D");
        completed.append("3");
    }

    @Order(4)
    @Test
    void testC() {
        System.out.println("Running test C");
        completed.append("4");
    }

}
