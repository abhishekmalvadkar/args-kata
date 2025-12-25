package com.amalvadkar.ak;

import java.util.List;
import java.util.stream.Stream;

public class StringToListTransformer {
    public static List<String> transform(String value) {
        return Stream.of(value.split(","))
                .toList();
    }
}
