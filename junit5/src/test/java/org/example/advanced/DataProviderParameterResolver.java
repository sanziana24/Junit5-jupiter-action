package org.example.advanced;

import org.example.advanced.Data.DefaultData;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class DataProviderParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
    throws ParameterResolutionException {

        return extensionContext.getRequiredTestMethod().isAnnotationPresent(DataProvider.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext)
    throws ParameterResolutionException {

        return new DefaultData(extensionContext);
    }
}
