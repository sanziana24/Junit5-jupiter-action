package org.example.advanced;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.example.advanced.MethodSourceProvider.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("The examples of @ParameterizedTest")
class ParameterizedTestTest {

    @Nested
    @DisplayName("Value source Parameterized Test")
    class NestedValuesSource {

        //very similar with DataProviderParameterResolverTest
        @ParameterizedTest
        @ValueSource(strings = {"Junit", "Jupiter"})
        void testValueSourceStrings(String value) {

            System.out.println(value);
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2})
        void testValueSourceInts(int value) {

            System.out.println(value);
        }
    }

    @Nested
    @DisplayName("Enum source Parameterized Test")
    class NestedEnumSource {

        @ParameterizedTest
        @EnumSource(TimeUnit.class)
        void testEnumSource(TimeUnit timeUnit) {

            System.out.println(timeUnit);
        }

        @ParameterizedTest
        @EnumSource(mode = EnumSource.Mode.INCLUDE, value = TimeUnit.class, names = {"SECONDS", "MINUTES"})
        void testEnumSourceInclude(TimeUnit timeUnit) {

            System.out.println(timeUnit);
        }
    }

    @DisplayName("CSV source Parameterized Test")
    @Nested
    class NestedCsvSource {

        @DisplayName("CSV Value Source test")
        @ParameterizedTest
        @CsvSource({"Java, 17", "Junit, 5", "Jupiter, 5.7", "Platform, 1.7"})
            //data can be loaded from external files, too => CSVFileSource
        void testCsvValueSource(String value, double version) {

            System.out.println(value + "---> " + version);
        }

        @ParameterizedTest
        @CsvFileSource(resources = {"/a.csv", "/b.csv"})
        void testCsvFileSource(String value, double version) {

            System.out.println(value + "---> " + version);
        }
    }

    @DisplayName("Method source Parameterized Test")
    @Nested
    class NestedMethodSource {

        /*
        Before use the test method source, we should define method data provider class to play the data provider role.
        This method has some requirements: must be static and must not be defined using private modifier.
         */

        @ParameterizedTest
        @MethodSource("org.example.advanced.MethodSourceProvider#stringDataProvider")
        void testStringMethodSource(String value) {

            System.out.println(value);
        }

        @ParameterizedTest
        @MethodSource("org.example.advanced.MethodSourceProvider#intDataProvider")
        void testIntegerMethodSource(int value) {

            System.out.println(value);
        }

        @ParameterizedTest
        @MethodSource("org.example.advanced.MethodSourceProvider#userDataProvider")
        void testUserMethodSource(User user) {

            System.out.println(user.getName());
            System.out.println(user.getAge());
        }

        @ParameterizedTest
        @MethodSource("org.example.advanced.MethodSourceProvider#argumentDataProvider")
        void testArgumentsMethodSource(String name, int age) {

            System.out.println(name);
            System.out.println(age);
        }
    }

    @DisplayName("Arguments Aggregation examples.")
    @Nested
    class NestedAgreggation {

        /*
        Arguments accessor to capture or hold all the parameters to a single one. From Arguments Accessor we can get
        every field/group of data defined
         */

        @ParameterizedTest
        @CsvSource({"Java, 17", "Junit, 5", "Jupiter, 5.7", "Platform, 1.7"})
        void testWithArgumentsAccessor(ArgumentsAccessor argumentsAccessor) {

            final var language = argumentsAccessor.getString(0);
            final var version = argumentsAccessor.getDouble(1);

            //combine these params to a Map

            final var map = Map.of("language", language, "version", version);

            System.out.println(map);
        }

        //another way - MapAggregator - more clean

        //OBS! needs to be annotated with AggregateWith - we capture and abstract the arguments capture source code
        // to another class file

        @ParameterizedTest
        @CsvSource({"Java, 17", "Junit, 5", "Jupiter, 5.7", "Platform, 1.7"})
        void testWithMapAggregator(@AggregateWith(MapAggregator.class) Map<String, Object> map) {

            System.out.println(map);
        }

        //instead of having this Aggregator like this, we can custom an annotation

        @ParameterizedTest
        @CsvSource({"Java, 17", "Junit, 5", "Jupiter, 5.7", "Platform, 1.7"})
        void testWithMapAnnotation(@CsvToMap Map<String, Object> map) {

            System.out.println(map);
        }

        @DisplayName("Arguments Sources examples.")
        @Nested
        class NestedArgumentsSource {

            @ParameterizedTest
            @ArgumentsSources({@ArgumentsSource(MyArgumentsProvider.class)})
            void testArgumentsProvider(String name, int age) {

                System.out.println(name + "---->" + age);
            }

        }

        static class MyArgumentsProvider implements ArgumentsProvider {

            @Override
            public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext)
            throws Exception {

                return Stream.of(Arguments.of("Alex", 37), Arguments.of("Jack", 18));
            }
        }

        @Nested
        @DisplayName("implicit conversion")
        class NestedImplicitConversion {

            @ParameterizedTest
            @ValueSource(strings = "true")
            void testBooleanImplicitConversion(Boolean value) {

                System.out.println(value);
                System.out.println(value.getClass());
            }

            @ParameterizedTest
            @ValueSource(strings = "SECONDS")
            void testEnumImplicitConversion(TimeUnit value) {

                System.out.println(value);
                System.out.println(value.getClass());
            }
        }

        //explicit conversion can be realized by implementing the ArgumentsConvertor interface

        @Nested
        @DisplayName("explicit conversion")
        class NestedExplicitConversion {

            @ParameterizedTest
            @EnumSource(TimeUnit.class)
            void testStringFromEnum(@ConvertWith(MyArgumentsConverter.class) String value) {

                System.out.println(value + "--->" + value.getClass());
            }

            //obs! how to ovveride the display name

            @DisplayName("[testStringFromUser demo arguments convertor]")
            @ParameterizedTest(name="{displayName}--[{index}]")
            @MethodSource("org.example.advanced.MethodSourceProvider#userDataProvider")
            void testStringFromUser(@ConvertWith(MyArgumentsConverter.class) String value) {

                System.out.println(value + "--->" + value.getClass());
            }
        }

        static class MyArgumentsConverter extends SimpleArgumentConverter {

            @Override
            protected Object convert(final Object o, final Class<?> aClass) throws ArgumentConversionException {
                return o.toString();
            }
        }

    }
}
