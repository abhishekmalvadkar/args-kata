package com.amalvadkar.ak;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public enum Schema {
    LOGGING("logging", "-l", "For logging", true, Boolean::parseBoolean, "^(true|false)$", "Only boolean value allowed"),
    VERBOSE("verbose", "-v", "For verbose", true, Boolean::parseBoolean, "^(true|false)$", "Only boolean value allowed"),
    PORT("port", "-p", "For port", 0, Integer::parseInt, "^\\d+$", "Only number value allowed"),
    LOG_DIR("logDir", "-d", "For log directory", "", value -> value, "^(/[^/ ]+)+/?$", "Only linux style directory value allowed");

    private static final Map<String, Schema> FLAG_TO_SCHEMA_MAP = Stream.of(values())
            .collect(Collectors.toMap(Schema::flag, Function.identity()));
    private static final List<String> SORTED_FLAGS = FLAG_TO_SCHEMA_MAP.keySet()
            .stream().sorted().toList();
    private static final String LINE_BREAK = "\n";
    private static final String VALID_FLAGS_INFO_MESSAGE = """
            Valid flags:
            
            %s""".formatted(validFlags());
    private static final String INVALID_FLAG_MESSAGE = """
            Invalid flag : %s
            
            %s""";

    private final String mappingName;
    private final String flag;
    private final String description;
    private final Object defaultValue;
    private final Function<String, Object> valueTransformer;
    private final String regex;
    private final String validValueMessage;

    public static Schema from(String flag) {
        if (isNotAvailableInSchema(flag)) {
            throw new InvalidFlagException(withInvalidFlagMessage(flag));
        }
        return FLAG_TO_SCHEMA_MAP.get(flag);
    }

    private static String withInvalidFlagMessage(String flag) {
        return INVALID_FLAG_MESSAGE.formatted(flag, VALID_FLAGS_INFO_MESSAGE);
    }

    private static boolean isNotAvailableInSchema(String flag) {
        return FLAG_TO_SCHEMA_MAP.get(flag) == null;
    }

    private static String validFlags() {
        List<String> flagInfos = new ArrayList<>();
        for (String flag : SORTED_FLAGS) {
            Schema schema = FLAG_TO_SCHEMA_MAP.get(flag);
            flagInfos.add("%s : %s".formatted(flag, schema.description));
        }
        return String.join(LINE_BREAK, flagInfos);
    }

    public String invalidValueMessage(String value) {
        return """
                %s is invalid value for %s flag
                
                %s""".formatted(
                value,
                flag,
                validValueMessage
                );
    }
}
