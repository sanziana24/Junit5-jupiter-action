package org.example.advanced;

/*
Using the RepetitionInfo object, we get the metadata at runtime when we adnotated the method
by @RepetitionTest
 */

import static org.junit.jupiter.api.RepeatedTest.LONG_DISPLAY_NAME;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

class RepetitionInfoParameterResolverTest {

    @DisplayName("prefix---")
    @RepeatedTest(value = 3, name = LONG_DISPLAY_NAME)
    void repeatTest(RepetitionInfo repetitionInfo) {

        System.out.println(repetitionInfo.getCurrentRepetition() + "--" + repetitionInfo.getTotalRepetitions());
        System.out.println("---------------------");
    }

}
