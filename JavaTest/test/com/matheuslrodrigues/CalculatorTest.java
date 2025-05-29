package com.matheuslrodrigues;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Test Math Operation in Calculator Class")
class CalculatorTest {

    Calculator calculator;

    @BeforeAll
    static void setup() {
        System.out.println("Executing @BeforeAll method.");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Executing @AfterAll method.");
    }

    @BeforeEach
    void beforeEachTestMethod() {
        System.out.println();
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method.");
    }

    @AfterEach
    void afterEachTestMethod() {
        System.out.println("Executing @AfterEach method.");
    }

    //test<System UnderTest>_<Condition or State Change>_<Expected Result>
    @Test
    @DisplayName("DividedByTwo")
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {
        System.out.println("Running test DividedByTwo");
        //    Arrange / Given
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;
        //    Act / When
        int actualResult = calculator.integerDivision(dividend, divisor);

        //    Assert / Then
        assertEquals(expectedResult, actualResult, "integerDivision() did not reach the correct results");

    }

    @Test
    @DisplayName("DividedByZero")
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException() {
        System.out.println("Running test DividedByZero");
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


    @DisplayName("Source Demonstration")
    @ParameterizedTest
    @ValueSource(strings = {"John", "Kate", "Alice"})
    void valueSourceDemonstration(String firstName){
        System.out.println(firstName);
        assertNotNull(firstName);
    }

    @DisplayName("Test Integer Subtraction")
    @ParameterizedTest
    //@MethodSource("integerSubtraction")
    /*@CsvSource({
            "33, 1, 32",
            "24, 1, 23",
            "54, 1, 53"
    })*/
    @CsvFileSource(resources = "/integerSubtraction.csv")
    void integerSubtraction(int minuend, int subtraEnd, int expectedResult) {
        System.out.println("Running Test " + minuend + " - " + subtraEnd + " = " + expectedResult);

        int actualResult = calculator.integerSubtraction(minuend, subtraEnd);

        assertEquals(expectedResult, actualResult, () -> minuend + "-" + subtraEnd + " did not result " + expectedResult);
    }

 /*   private static Stream<Arguments> integerSubtraction() {
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(24, 1, 23),
                Arguments.of(54, 1, 53)
        );
    }
*/
}