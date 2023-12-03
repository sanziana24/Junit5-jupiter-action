package org.example.advanced;

import java.util.Random;
import java.util.stream.IntStream;

public class SimpleService {

    public IntStream getDataFromRemoteServer() {

        final var random = new Random();

        return random.ints(0, 100)
                     .limit(random.nextInt(10));
    }
}
