package org.example.advanced;

import org.junit.jupiter.api.DisplayName;

@DisplayName("custom the test template for simulate @RepeatedTest")
class RepeatTestTemplate {

    @Repeat(3)
    void firstTest(RepeatInfo repeatInfo) {

        System.out.println(repeatInfo);
    }

    @Repeat(3)
    void secondTest() {

        System.out.println("second test");
    }
}
