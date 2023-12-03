package org.example.advanced;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("<Simple> TestTemplateInvocationContextProvider Demo")
class MyTestTemplateInvocationContextProviderTest {

    @DisplayName("simple test case for use custom test template")
    @HelloWorld
        //@TestTemplate //CAN BE ADDED AT HelloWorld annotation
        // @ExtendWith(MyTestTemplateInvocationContextProvider.class) //CAN BE ADDED AT HelloWorld annotation
    void simpleTest(String str) {

        System.out.println(str);
        Assertions.assertTrue(Arrays.asList("hello", "world")
                                    .contains(str));
    }
}
