package org.example.advanced;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

public class RepeatTestTemplateInvocationContextProvider implements TestTemplateInvocationContextProvider {

    //supports only test methods annotated by @Repeat
    @Override
    public boolean supportsTestTemplate(final ExtensionContext context) {
        return context.getRequiredTestMethod()
                      .isAnnotationPresent(Repeat.class);
    }

    //implement how many times the test will be invoked/executed.
    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(final ExtensionContext context) {

        //the repeat times will be set into this annotation
        final var repeat = context.getRequiredTestMethod()
                                  .getDeclaredAnnotation(Repeat.class);
        final var maxTimes = repeat.value();

        return IntStream.rangeClosed(1, maxTimes)
                        .mapToObj(e -> new DefaulRepeatInfo(e, maxTimes))
                        .map(this::invocationContext);
    }

    private TestTemplateInvocationContext invocationContext(RepeatInfo repeatInfo) {

        return new TestTemplateInvocationContext() {

            @Override
            public String getDisplayName(final int invocationIndex) {

                return String.format("[%d-%d]", repeatInfo.current(), repeatInfo.total());
            }

            @Override
            public List<Extension> getAdditionalExtensions() {

                return Collections.singletonList(new ParameterResolver() {

                    @Override
                    public boolean supportsParameter(final ParameterContext parameterContext,
                        final ExtensionContext extensionContext) throws ParameterResolutionException {

                        return extensionContext.getRequiredTestMethod()
                                               .isAnnotationPresent(Repeat.class);
                    }

                    @Override
                    public Object resolveParameter(final ParameterContext parameterContext,
                        final ExtensionContext extensionContext) throws ParameterResolutionException {

                        if (parameterContext.getParameter()
                                            .getType() == RepeatInfo.class) {
                            return repeatInfo;
                        }
                        return null;
                    }
                });
            }
        };
    }

    private static class DefaulRepeatInfo implements RepeatInfo {

        private final int current;
        private final int total;

        private DefaulRepeatInfo(final int current, final int total) {
            this.current = current;
            this.total = total;
        }

        @Override
        public int current() {
            return this.current;
        }

        @Override
        public int total() {
            return this.total;
        }

        @Override
        public String toString() {
            return "DefaulRepeatInfo{" + "current=" + current + ", total=" + total + '}';
        }
    }
}
