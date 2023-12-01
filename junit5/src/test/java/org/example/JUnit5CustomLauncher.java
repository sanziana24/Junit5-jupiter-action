package org.example;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.PrintWriter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class JUnit5CustomLauncher {

    public static void main(String[] args) {

        //define a launch discovery request builder and select the source code

        final var request = LauncherDiscoveryRequestBuilder.request()
                                                           .selectors(selectPackage("org.example"),
                                                               selectClass(Junit5StandardTest.class))
                                                           .build();

        final var launcher = LauncherFactory.create();

        final var listener = new SummaryGeneratingListener();

        launcher.registerTestExecutionListeners(listener);

        //the launcher will be triggered - will execute the request
        launcher.execute(request);

        //after the trigger complette, we get info from listeners
        listener.getSummary().printTo(new PrintWriter(System.out));
    }

}
