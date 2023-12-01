package org.example;

import static junit.framework.Assert.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class Junit5StandardTest {

    @Test
    void listShouldContainsElements() {

        //given
        final var list = List.of("JAVA", "JUPITER", "JUNIT");
        final var element = "JAVA";
        //when
        var existing = list.contains(element);
        //then
        assertTrue(existing);
    }
}
