package org.example.advanced;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

public class MyTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

    //we'll very if annotaton (HelloWorld) supports or not the test template
    @Override
    public boolean supportsTestTemplate(final ExtensionContext context) {

        return context.getRequiredTestMethod()
                      .isAnnotationPresent(HelloWorld.class);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(final ExtensionContext context) {

        return Stream.of(invocationContext("hello"), invocationContext("world"));
    }

    private TestTemplateInvocationContext invocationContext(String str) {

        //we'll implement TestTemplateInvocationContext's methods, because we want to add parameters to TestTemplate
        // method. We need to register some Parameter Resolver.

        return new TestTemplateInvocationContext() {

            @Override
            public String getDisplayName(final int invocationIndex) {
                return "test template " + str;
            }

            //in this method we register/add multiple extensions
            //in this case, only one Parameter Resolver is needed

            @Override
            public List<Extension> getAdditionalExtensions() {

                return Arrays.asList(new ParameterResolver() {

                    @Override
                    public boolean supportsParameter(final ParameterContext parameterContext,
                        final ExtensionContext extensionContext) throws ParameterResolutionException {

                        return parameterContext.getParameter()
                                               .getType() == String.class && extensionContext.getRequiredTestMethod()
                                                                                             .isAnnotationPresent(
                                                                                                 HelloWorld.class);
                    }

                    @Override
                    public Object resolveParameter(final ParameterContext parameterContext,
                        final ExtensionContext extensionContext) throws ParameterResolutionException {

                        return str;
                    }
                });
            }
        };
    }
}
