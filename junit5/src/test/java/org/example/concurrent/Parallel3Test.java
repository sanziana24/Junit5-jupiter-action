package org.example.concurrent;

import static java.lang.Thread.currentThread;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("group3")
class Parallel3Test {

    @Test
    void test3A(){

        System.out.println(currentThread() + "---->test3A in Parallel3Test");
    }

    @Test
    void test3B(){

        System.out.println(currentThread() + "---->test3B in Parallel3Test");
    }
}
