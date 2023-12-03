package org.example.concurrent;

import static java.lang.Thread.currentThread;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("group2")
class Parallel2Test {

    @Test
    void test2A(){

        System.out.println(currentThread() + "---->test2A in Parallel2Test");
    }

    @Test
    void test2B(){

        System.out.println(currentThread() + "---->test2B in Parallel2Test");
    }
}
