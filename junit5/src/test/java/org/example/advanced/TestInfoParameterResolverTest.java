package org.example.advanced;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
/*
Using TestInfoParameterResolver we can pass TestInfo into test method/lifecycle method.
This means allow dependency injection into our source code.
 */

@DisplayName("The simple example of TestInfoParameterResolver")
class TestInfoParameterResolverTest {

    @DisplayName("display name of init@BeforeAll")
    @BeforeAll
    static void init(TestInfo testInfo) {

        System.out.println("-----------------------");
        System.out.println(testInfo.getDisplayName());
        System.out.println(testInfo.getTestClass());
        System.out.println(testInfo.getTestMethod()); //Optional.empty
        System.out.println(testInfo.getTags());
        System.out.println("-----------------------");
    }

    @DisplayName("display name of setUp@BeforeEach")
    @BeforeEach
    @Tag("123")
    @Tag("abc")
    void setUp(TestInfo testInfo) {

        System.out.println("=======================");
        System.out.println(testInfo.getDisplayName());
        System.out.println(testInfo.getTestClass());
        System.out.println(testInfo.getTestMethod());
        System.out.println(testInfo.getTags());
        System.out.println("=======================");
    }


    @Test
    @DisplayName("test case ")
    @Tag("simple-test")
    void simpleTest(TestInfo testInfo){

        System.out.println("***********************");
        System.out.println(testInfo.getDisplayName());
        System.out.println(testInfo.getTestClass());
        System.out.println(testInfo.getTestMethod());
        System.out.println(testInfo.getTags());
        System.out.println("***********************");
    }
}
