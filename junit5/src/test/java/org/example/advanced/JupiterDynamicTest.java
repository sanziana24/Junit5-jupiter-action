package org.example.advanced;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/*
Example of usual test approach for the random data returned by the SimpleService method.
 */

@DisplayName("The Jupiter Dynamic Test Feature")
class JupiterDynamicTest {

    private SimpleService simpleService;

    @BeforeEach
    void setUp() {

        this.simpleService = new SimpleService();
        System.out.println("lifecycle setup @BeforeEach");
    }

    @DisplayName("static test")
    @Test
    void shouldGetDataFromRemoteServerAndValid() {

        //Generate only one test and prints in the console the elements
        //assert every element in one test case

        this.simpleService.getDataFromRemoteServer()
                          .forEach(e -> {
                              System.out.printf("test the element [%d] is valid%n", e);
                              assertTrue(e > 0);
                          });
    }

    @DisplayName("dynamic test")
    @TestFactory
    Stream<DynamicTest> shouldGetDataFromRemoteServerAndValidByDynamic() {

        //map to object to convert to a dynamical test
        //at different run time, a different number of dynamic tests will be generated.
        //assert every element in a different test case

        return this.simpleService.getDataFromRemoteServer()
                                 .mapToObj(e -> dynamicTest(String.format("test the element [%d] is valid", e), () -> {
                                     assertTrue(e > 0);
                                 }));
    }

    @DisplayName("dynamic test from collection")
    @TestFactory
    Collection<DynamicTest> dynamicTestFromCollection() {

        return Arrays.asList(dynamicTest("1st dynamic test", () -> assertTrue(true)));
    }

    @DisplayName("dynamic test from Iterable")
    @TestFactory
    Iterable<DynamicTest> dynamicTestFromIterable() {

        return Arrays.asList(dynamicTest("2nd dynamic test", () -> assertTrue(true)));
    }

    @DisplayName("dynamic test from Iterator")
    @TestFactory
    Iterator<DynamicTest> dynamicTestFromIterator() {

        return Arrays.asList(dynamicTest("3rd dynamic test", () -> assertTrue(true)))
                     .iterator();
    }

    @DisplayName("dynamic test Array")
    @TestFactory
    DynamicTest[] dynamicTestFromArray() {

        return new DynamicTest[]{dynamicTest("4th dynamic test", () -> assertTrue(true))};
    }

    // see tree structure, tree and leaf
    // dynamic tests will not follow the lifecycle rules - Before each will be executed at method level - only one
    // time, not before each dynamic test is executed

    @DisplayName("dynamic container")
    @TestFactory
    DynamicContainer dynamicTestWithContainer() {

        return dynamicContainer("root container",
            Stream.of(dynamicTest("first level dynamic test", () -> assertTrue(true)),
                dynamicContainer("second level dynamic container",
                    Stream.of(dynamicTest("second level <1> dynamic test", () -> assertTrue(true)),
                        dynamicTest("second level <2> dynamic test", () -> assertTrue(true))))));
    }


}
