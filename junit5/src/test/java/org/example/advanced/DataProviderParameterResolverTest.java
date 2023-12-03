package org.example.advanced;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

//@ExtendWith(DataProviderParameterResolver.class) //add here or in DataProvider interface
class DataProviderParameterResolverTest {

    @DataProvider({"Hello", "World", "Java", "Junit", "Jupiter"})
    @Test
    @RepeatedTest(10)
    void simpleTestDataProviderParameterResolver(Data data) {

        System.out.println(data.getValue());
    }
}
