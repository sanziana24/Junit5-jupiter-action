package org.example.fundamental;

import static junit.framework.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.function.Executable;

/*
    @Disabled - ignore test (skip it); also display the message in the console
    @DisplayName - the name will be used when displaying running tests in the console instead of default naming by
    method/class name.
    @Order - used to control the execution order! But its not recommended since each unit test should run independently
           - at class level, the @TestMethodOrder annotation should be added
    @RepeatedTest - used for multiple test repetitions
                  - we can go further and use the RepetitionInfo in our tests
                  - inside Jupiter, the repeated test is about parameter resolver
    @Nested - mark inner classes (group of several related test cases in one inner class)
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName(" <The unit test for JAVA Immutable list features>")
class ImmutableListTest {

    private final List<String> LIST = List.of("HELLO", "JAVA", "JUNIT", "JUPITER");

    @DisplayName("basic usage of jupiter")
    @Nested
    class NestedBasic {

        @Order(10)
        @DisplayName("the list size should be 4")
        @Test
        void listShouldBeFour() {

            var size = LIST.size();
            Assertions.assertEquals(4, size);
        }

        @Order(100)
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
    }

    @Nested
    @DisplayName("about exception assertion examples")
    class NestedExceptionAssertion {


        @Order(1)
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
        @Test
        @Disabled("disable this test method due to always failed")
        void immutableListOnlySupportReadOperation() {

            // fails the first and the last two will not be executed.
            //        Assertions.assertEquals("HELLO", LIST.remove(0)); //throws error
            //        Assertions.assertTrue(() -> LIST.contains("JAVA"));
            //        Assertions.assertTrue(() -> LIST.contains("JUNIT"));

            //last two statements are processed by batch way

            Assertions.assertAll(() -> Assertions.assertEquals("HELLO", LIST.remove(0)), () -> {
                Assertions.assertTrue(() -> LIST.contains("JAVA"));
                System.out.println("contains java");
            }, () -> {
                Assertions.assertTrue(() -> LIST.contains("JUNIT"));
                System.out.println("contains junit");
            });
        }
    }

    @Nested
    class NestedRepeatable {

        @RepeatedTest(3)
        void repeatedTest() {

            Assertions.assertTrue(() -> LIST.contains("JAVA"));
        }

        @RepeatedTest(4)
        void repeatWithIndex(RepetitionInfo info) {

            //index starts at 1 in execution
            final var expectedValue = switch (info.getCurrentRepetition()) {
                case 1:
                    yield "HELLO";
                case 2:
                    yield "JAVA";
                case 3:
                    yield "JUNIT";
                case 4:
                    yield "JUPITER";
                default:
                    yield "N/A";
            };

            Assertions.assertEquals(expectedValue, LIST.get(info.getCurrentRepetition() - 1));
        }

        @DisplayName("Repeat assert immutable list elements ==>")
        @RepeatedTest(value = 4, name = "{displayName} {currentRepetition}/{totalRepetitions}")
        void repeatWithDetailsInformation(RepetitionInfo info) {

            //index starts at 1 in execution
            final var expectedValue = switch (info.getCurrentRepetition()) {
                case 1:
                    yield "HELLO";
                case 2:
                    yield "JAVA";
                case 3:
                    yield "JUNIT";
                case 4:
                    yield "JUPITER";
                default:
                    yield "N/A";
            };

            Assertions.assertEquals(expectedValue, LIST.get(info.getCurrentRepetition() - 1));
        }
    }

    @Nested
    class NestedTimeout {

        @Test
        @Timeout(value = 5, unit = TimeUnit.SECONDS)
        void timeoutAssertion() {

            final var blockerQueue = new ArrayBlockingQueue<String>(5);
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                blockerQueue.offer("TEST");
            }).start();

            assertTimeout(Duration.ofSeconds(2), () -> {
                assertEquals("TEST", blockerQueue.take());
            }); //EXPECT THAT RESULT TO BE RETRIEVED IN 2 SECONDS.
        }

        @Test
        @Disabled
        void timeoutPreemptivelyAssertion() {

            final var blockerQueue = new ArrayBlockingQueue<String>(5);

            assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
                assertEquals("TEST", blockerQueue.take());
            }); //EXPECT THAT RESULT TO BE RETRIEVED IN 2 SECONDS.
        }
    }

    @Nested
    class NestedAssumptions {

        @DisplayName("the test cse will be execution only sit env.")
        @Test
        void shouldBeExecutedOnlySit() {

            final String env = System.getenv()
                                     .getOrDefault("ENV", "N/A"); //ENV is set in run configuration/pom.xml: ENV=sit
            Assumptions.assumeTrue("sit".equalsIgnoreCase(env));

            //If the assumption is true => the following code will be executed, otherwise has the same behaviour as
            // @Disabled (will ignore)

            final var blockerQueue = new ArrayBlockingQueue<String>(5);
            //
            //        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            //            assertEquals("TEST", blockerQueue.take());
            //        }); //EXPECT THAT RESULT TO BE RETRIEVED IN 2 SECONDS.

            // same about source code, but more functional style
            Assumptions.assumingThat(() -> "sit".equalsIgnoreCase(env), () -> {
                assertTimeoutPreemptively(Duration.ofSeconds(2), () ->
                    assertEquals("TEST", blockerQueue.take())
                );
            });
        }

        @Nested
        @DisplayName("more level nested")
        class InnerNestedClass{

            @Order(10)
            @DisplayName("the list size should be 4")
            @Test
            void listShouldBeFour() {

                var size = LIST.size();
                Assertions.assertEquals(4, size);
            }
        }
    }
}
