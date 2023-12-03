package org.example.advanced;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface Data {

    String getValue();

    class DefaultData implements Data {

        private final ExtensionContext context;

        public DefaultData(final ExtensionContext context) {
            this.context = context;
        }

        @Override
        public String getValue() {

            final var testMethod = this.context.getRequiredTestMethod();
            final var dataProvider = testMethod.getDeclaredAnnotation(DataProvider.class);
            final var dataArray = dataProvider.value();

            if (dataArray.length == 0) {
                return "empty";
            }
            return dataArray[ThreadLocalRandom.current()
                                              .nextInt(dataArray.length)];
        }
    }
}
