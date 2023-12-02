package org.example.fundamental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;

/*
OBS! The hook methods supports parameters such as TestInfo
There are some handlers overriden in JupiterInstanceLifecycle interface
 */

@ExtendWith({JupiterInstanceLifecycle.MyTestInstancePostProcessor.class,
    JupiterInstanceLifecycle.MyBeforeAllCallback.class, JupiterInstanceLifecycle.MyBeforeEachCallback.class,
    JupiterInstanceLifecycle.MyBeforeTestExecutionCallback.class,
    JupiterInstanceLifecycle.MyTestExecutionExceptionHandler.class,
    JupiterInstanceLifecycle.MyAfterTestExecutionHandler.class, JupiterInstanceLifecycle.MyAfterEachCallback.class,
    JupiterInstanceLifecycle.MyAfterAllCallback.class

})
@TestInstance(Lifecycle.PER_CLASS) //dont forget
@DisplayName("<Jupiter test class instance lifecycle>")
class JupiterInstanceLifecycleTest {

    private static List<String> LIST;

    @BeforeAll
    static void init() {

        LIST = List.of("HELLO", "JAVA", "JUNIT", "JUPITER");
        System.out.println("<init> annotated by @BeforeAll");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {

        System.out.println("<set up " + testInfo.getTestMethod()
                                                .get() + " >");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {

        System.out.println("<tear down " + testInfo.getTestMethod()
                                                   .get() + ">");
    }

    @AfterAll
    static void destroy() {

        System.out.println("<init> annotated by @AfterAll");
    }

    @DisplayName("the list size should be 4")
    @Test
    void listShouldBeFour() {

        var size = LIST.size();
        assertEquals(4, size);
    }

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

    @DisplayName("[batch assertions] the java immutable list not support change since create")
    @Disabled("disable this test method due to always failed")
    @Test
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
