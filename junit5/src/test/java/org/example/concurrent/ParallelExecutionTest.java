package org.example.concurrent;

import static java.lang.Thread.currentThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;

/*
    junit.jupiter.execution.parallel.enabled=true should remain uncommented in junit-platform.properties
 */
@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Parallel Execution Control by @Execution")
class ParallelExecutionTest {

    private static List<String> sharedResource;
    private static final String MY_RESOURCE = "ParallelExecutionTest.class.getName()";

    //@BeforeAll makes sure that is a singleton

    @BeforeAll
    static void setUp() {

        sharedResource = new ArrayList<>(); //ArrayList is thread-unsafe data structure
    }

    @ResourceLock(value = MY_RESOURCE, mode = ResourceAccessMode.READ_WRITE)
    @Test
    void testExecutionMethod1() throws InterruptedException {

        TimeUnit.SECONDS.sleep(1);
        sharedResource.add("testExecutionMethod1");
        System.out.println(currentThread() + " testExecutionMethod1.");
    }

    @ResourceLock(value = MY_RESOURCE, mode = ResourceAccessMode.READ_WRITE)
    @Test
    void testExecutionMethod2() throws InterruptedException {

        TimeUnit.MINUTES.sleep(1);
        sharedResource.add("testExecutionMethod2");
        System.out.println(currentThread() + " testExecutionMethod2.");
    }

}
