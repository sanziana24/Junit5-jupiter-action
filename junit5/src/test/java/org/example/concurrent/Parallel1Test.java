package org.example.concurrent;

import static java.lang.Thread.currentThread;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("group1")
class Parallel1Test {

    @Test
    void test1A(){

        System.out.println(currentThread() + "---->test1A in Parallel1Test");
    }

    @Test
    void test1B(){

        System.out.println(currentThread() + "---->test1B in Parallel1Test");
    }
}
