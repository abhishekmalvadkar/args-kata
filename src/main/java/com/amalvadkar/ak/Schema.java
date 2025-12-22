package com.amalvadkar.ak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum Schema {
    LOGGING("logging", "-l",true, Boolean::parseBoolean),
    VERBOSE("verbose", "-v", true, Boolean::parseBoolean),
    PORT("port", "-p", 0, Integer::parseInt),
    LOG_DIR("logDir", "-d", "", value -> value);

    private static final Map<String, Schema> FLAG_TO_SCHEMA_MAP = Stream.of(values())
            .collect(Collectors.toMap(Schema::flag, Function.identity()));

    private final String mappingName;
    private final String flag;
    private final Object defaultValue;
    private final Function<String, Object> valueTransformer;

    public static Schema from(String flag){
        return FLAG_TO_SCHEMA_MAP.get(flag);
    }
}
