package com.amalvadkar.ak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.security.InvalidParameterException;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum Schema {
    LOGGING("logging", "-l",true, Boolean.class),
    VERBOSE("verbose", "-v", true, Boolean.class),
    PORT("port", "-p", 0, Integer.class),
    LOG_DIR("logDir", "-d", "", String.class);

    private final String mappingName;
    private final String flag;
    private final Object defaultValue;
    private final Class<?> type;

    public static Schema from(String flag){
       return Arrays.stream(values())
                .filter(schema -> schema.flag.equals(flag))
                .findFirst()
                .orElseThrow(() -> new InvalidParameterException("invalid flag"));
    }
}
