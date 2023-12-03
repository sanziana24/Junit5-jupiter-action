package org.example.advanced;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class MethodSourceProvider {

    static Stream<String> stringDataProvider() {

        return Stream.of("Java", "Junit");
    }

    static IntStream intDataProvider() {

        return IntStream.rangeClosed(1, 5);
    }

    static Stream<User> userDataProvider() {

        return Stream.of(new User("Alex", 36), new User("Jack", 18));
    }

    // another option to User class with 2 properties

    static Stream<Arguments> argumentDataProvider() {

        return Stream.of(Arguments.of("Alex", 36), Arguments.of("Jack", 18));
    }

    static class User {

        private final String name;
        private final int age;

        User(final String name, final int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
        }
    }
}
