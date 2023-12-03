package org.example.advanced;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

/*
Jupiter engine capture or consume this message publisher by our test method. Very similar to log4j log the message
that we send display name.
 */
class TestReporterParameterResolverTest {

    @DisplayName("simple display name")
    @Test
    void simpleTest(TestReporter reporter, TestInfo testInfo){

        reporter.publishEntry("DisplayName", testInfo.getDisplayName());
    }
}
