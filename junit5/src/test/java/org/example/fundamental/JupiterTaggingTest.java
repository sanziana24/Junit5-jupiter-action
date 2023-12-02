package org.example.fundamental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;



/*
    @Tag - help to select which tests to execute
         - examples filter expressions:
                    product -> all tests for product
                     catalog | shipping -> all tests for catalog plus all tests for shipping
                     catalog & shipping -> all tests for he intersection between catalog and shipping
                     product & !end-to-end -> all tests for product, but not the end-to-end tests
                     (micro | integration) & (product | shipping) -> all micro or integration tests for product or
                     shipping
         - tag filter expressions that should be executed are defined in pom.xml and run with mvn test command
         - obs! method inherit the class @Tag annotation!
 */
@Tags({@Tag("dev"), @Tag("sit"), @Tag("uat")})
class JupiterTaggingTest {

    private static List<String> LIST;

    @BeforeAll
    public static void init() {
        LIST = List.of("HELLO", "JAVA", "JUNIT", "JUPITER");
    }

    @Tag("dev")
    @DisplayName("the list size should be 4")
    @Test
    void listShouldBeFour() {

        var size = LIST.size();
        assertEquals(4, size);
    }

    @Tags({
        @Tag("sit")
    })
    @DisplayName("the list should be contains JAVA element.")
    @Test
    void listShouldBeContainsJava() {

        //given
        final var JAVA = "JAVA";

        //when
        //2 equivalent options
        final var existing = LIST.contains(JAVA);
        Assertions.assertTrue(existing);
        Assertions.assertTrue(() -> LIST.contains(JAVA));
    }

    @Tag("sit")
    @Tag("uat")
    @DisplayName("the java immutable list not support change since create")
    @Test
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

    @Tag("prod")
    @DisplayName("[batch assertions] the java immutable list not support change since create")
    @Test
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
