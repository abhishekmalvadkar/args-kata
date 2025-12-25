package com.amalvadkar.ak;

import java.util.List;
import java.util.stream.Stream;

public class StringToListTransformer {

    private static final String LIST_FLAG_VALUE_SEPARATOR = ",";

    public static List<String> transform(String value) {
        return Stream.of(value.split(LIST_FLAG_VALUE_SEPARATOR))
                .toList();
    }
}
