package com.matheuslrodrigues;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DemoRepeatedTest {

    Calculator calculator;

    @BeforeEach
    void beforeEachTestMethod() {
        System.out.println();
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method.");
    }

    @DisplayName("DividedByZero")
    @RepeatedTest(value = 3, name = "{displayName}, Repetition {currentRepetition} of " + "{totalRepetitions}")
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println("Running " + testInfo.getTestMethod().get().getName());
        System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());
        //Arrange
        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";
        //Act & Assert
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            //Act
            calculator.integerDivision(dividend, divisor);
        }, "Division by zero should have thrown an Arithmetic exception");
        //Assert
        assertEquals(expectedExceptionMessage, actualException.getMessage(), "Unexpected exception message");

    }
}
