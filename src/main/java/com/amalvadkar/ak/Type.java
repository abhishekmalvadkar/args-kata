package com.amalvadkar.ak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum Type {
    BOOLEAN(true, Boolean::parseBoolean, "^(true|false)$", "Only boolean value allowed"),
    NUMBER( 0, Integer::parseInt, "^\\d+$", "Only number value allowed"),
    STRING("", value -> value, "^.*$", ""),
    DIRECTORY(STRING.defaultValue, STRING.valueTransformer, "^(/[^/ ]+)+/?$", "Only linux style directory value allowed");

    private final Object defaultValue;
    private final Function<String, Object> valueTransformer;
    private final String regex;
    private final String validValueMessage;

}
