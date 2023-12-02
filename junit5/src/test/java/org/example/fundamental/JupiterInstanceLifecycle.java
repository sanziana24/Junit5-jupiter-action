package org.example.fundamental;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/*
Customizing the callbacks
 */

public interface JupiterInstanceLifecycle {

    class MyTestInstancePostProcessor implements TestInstancePostProcessor {


        @Override
        public void postProcessTestInstance(final Object o, final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyTestInstancePostProcessor--postProcessTestInstance---" + o);
            System.out.println(extensionContext.getTestInstanceLifecycle());
            System.out.println("--MyTestInstancePostProcessor--postProcessTestInstance---" + o);
        }
    }

    class MyBeforeAllCallback implements BeforeAllCallback {

        @Override
        public void beforeAll(final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyNeforeAllCallback--beforeAll---");
        }
    }

    class MyBeforeEachCallback implements BeforeEachCallback {

        @Override
        public void beforeEach(final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyBeforeEachCallback--beforeEach---");
        }
    }

    class MyBeforeTestExecutionCallback implements BeforeTestExecutionCallback {

        @Override
        public void beforeTestExecution(final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyBeforeTestExecutionCallback--beforeTestExecution---");
        }
    }

    class MyTestExecutionExceptionHandler implements TestExecutionExceptionHandler {

        @Override
        public void handleTestExecutionException(final ExtensionContext extensionContext, final Throwable throwable)
        throws Throwable {

            System.out.println("--MyTestExecutionExceptionHandler--handleTestExecutionException---" + throwable);
        }
    }

    class MyAfterTestExecutionHandler implements AfterTestExecutionCallback {


        @Override
        public void afterTestExecution(final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyAfterTestExecutionCallback--afterTestExecution");
        }
    }

    class MyAfterEachCallback implements AfterEachCallback {

        @Override
        public void afterEach(final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyAfterEachCallback--afterEach---");
        }
    }

    class MyAfterAllCallback implements AfterAllCallback {

        @Override
        public void afterAll(final ExtensionContext extensionContext) throws Exception {

            System.out.println("--MyAfterAllCallback--afterAll---");
        }
    }
}
