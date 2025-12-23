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

    private final String mappingName;
    private final String flag;
    private final String description;
    private final Object defaultValue;
    private final Function<String, Object> valueTransformer;

    public static Schema from(String flag){
        Schema schema = FLAG_TO_SCHEMA_MAP.get(flag);
        if (schema == null) {
            String message = """
                    Invalid flag : %s
                    
                    %s""".formatted(flag, info());
            throw new InvalidFlagException(message);
        }
        return schema;
    }

    public static String info(){
        List<String> flagInfos = new ArrayList<>();
        for (String flag : sortedFlags()) {
            Schema schema = FLAG_TO_SCHEMA_MAP.get(flag);
            flagInfos.add("%s : %s".formatted(flag, schema.description));
        }
        return """
                Valid flags:
                
                %s""".formatted(String.join("\n", flagInfos));
    }

    private static List<String> sortedFlags() {
        return FLAG_TO_SCHEMA_MAP.keySet()
                .stream()
                .sorted()
                .toList();
    }
}
