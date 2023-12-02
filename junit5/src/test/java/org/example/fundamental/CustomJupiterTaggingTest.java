package org.example.fundamental;

import static org.example.fundamental.Env.All;
import static org.example.fundamental.Env.Prod;
import static org.example.fundamental.Env.Sit;
import static org.example.fundamental.Env.Uat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.example.fundamental.Env.Dev;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

/*
The tags from JupiterTaggingTest are adapted to the annotations defined in Env enum.
 */

@All
class CustomJupiterTaggingTest {

    private static List<String> LIST;

    @BeforeAll
    public static void init() {
        LIST = List.of("HELLO", "JAVA", "JUNIT", "JUPITER");
    }

    @Dev
    @DisplayName("the list size should be 4")
    void listShouldBeFour() {

        var size = LIST.size();
        assertEquals(4, size);
    }

    @Sit
    @DisplayName("the list should be contains JAVA element.")
    void listShouldBeContainsJava() {

        //given
        final var JAVA = "JAVA";

        //when
        //2 equivalent options
        final var existing = LIST.contains(JAVA);
        Assertions.assertTrue(existing);
        Assertions.assertTrue(() -> LIST.contains(JAVA));
    }

    @Sit
    @Uat
    @DisplayName("the java immutable list not support change since create")
    void immutableListSHouldNotBeUpdatedSinceCreate() {

        //given
        var index = 0;

        //when

        Executable executable = () -> {
            LIST.remove(index);
            Assertions.fail("should not process at here.");
        };

        Assertions.assertThrows(UnsupportedOperationException.class, executable);
    }

    @Prod
    @DisplayName("[batch assertions] the java immutable list not support change since create")
    @Disabled("disable this test method due to always failed")
    void immutableListOnlySupportReadOperation() {

        // fails the first and the last two will not be executed.
        //        Assertions.assertEquals("HELLO", LIST.remove(0)); //throws error
        //        Assertions.assertTrue(() -> LIST.contains("JAVA"));
        //        Assertions.assertTrue(() -> LIST.contains("JUNIT"));

        //last two statements are processed by batch way

        Assertions.assertAll(() -> assertEquals("HELLO", LIST.remove(0)),
            () -> Assertions.assertTrue(() -> LIST.contains("JAVA")),
            () -> Assertions.assertTrue(() -> LIST.contains("JUNIT")));
    }
}
