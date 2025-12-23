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
    LOGGING("logging", "-l","For logging",true, Boolean::parseBoolean),
    VERBOSE("verbose", "-v","For verbose", true, Boolean::parseBoolean),
    PORT("port", "-p","For port",0, Integer::parseInt),
    LOG_DIR("logDir", "-d","For log directory", "", value -> value);

    private static final Map<String, Schema> FLAG_TO_SCHEMA_MAP = Stream.of(values())
            .collect(Collectors.toMap(Schema::flag, Function.identity()));
    private static final String LINE_BREAK = "\n";

    private final String mappingName;
    private final String flag;
    private final String description;
    private final Object defaultValue;
    private final Function<String, Object> valueTransformer;

    public static Schema from(String flag){
        if (isNotAvailableInSchema(flag)) {
            throw new InvalidFlagException(withInvalidFlagMessage(flag));
        }
        return FLAG_TO_SCHEMA_MAP.get(flag);
    }

    private static String withInvalidFlagMessage(String flag) {
        return """
                Invalid flag : %s
                
                %s""".formatted(flag, validFlagsInfo());
    }

    private static boolean isNotAvailableInSchema(String flag) {
        return FLAG_TO_SCHEMA_MAP.get(flag) == null;
    }

    private static String validFlagsInfo(){
        return """
                Valid flags:
                
                %s""".formatted(String.join(LINE_BREAK, prepareValidFlagInfos()));
    }

    private static List<String> prepareValidFlagInfos() {
        List<String> flagInfos = new ArrayList<>();
        for (String flag : sortedFlags()) {
            Schema schema = FLAG_TO_SCHEMA_MAP.get(flag);
            flagInfos.add("%s : %s".formatted(flag, schema.description));
        }
        return flagInfos;
    }

    private static List<String> sortedFlags() {
        return FLAG_TO_SCHEMA_MAP.keySet()
                .stream()
                .sorted()
                .toList();
    }
}
