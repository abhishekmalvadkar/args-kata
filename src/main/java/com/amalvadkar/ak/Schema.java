package com.amalvadkar.ak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum Schema {
    LOGGING("logging", "-l",true, Boolean::parseBoolean),
    VERBOSE("verbose", "-v", true, Boolean::parseBoolean),
    PORT("port", "-p", 0, Integer::parseInt),
    LOG_DIR("logDir", "-d", "", value -> value);

    private final String mappingName;
    private final String flag;
    private final Object defaultValue;
    private final Function<String, Object> valueTransformer;

    public static Schema from(String flag){
       return Arrays.stream(values())
                .filter(schema -> schema.flag.equals(flag))
                .findFirst()
                .orElseThrow(() -> new InvalidParameterException("invalid flag"));
    }
}
