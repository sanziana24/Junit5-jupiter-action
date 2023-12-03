package org.example.advanced;

import java.util.Map;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

public class MapAggregator implements ArgumentsAggregator {

    @Override
    public Object aggregateArguments(final ArgumentsAccessor argumentsAccessor, final ParameterContext parameterContext)
    throws ArgumentsAggregationException {

        return Map.of("language", argumentsAccessor.get(0), "version", argumentsAccessor.getDouble(1));
    }
}
