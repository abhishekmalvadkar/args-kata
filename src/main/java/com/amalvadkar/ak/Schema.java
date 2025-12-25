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
    LOGGING("logging", "-l", "For logging", Type.BOOLEAN),
    VERBOSE("verbose", "-v", "For verbose", Type.BOOLEAN),
    PORT("port", "-p", "For port", Type.NUMBER),
    LOG_DIR("logDir", "-d", "For log directory", Type.DIRECTORY),
    PROFILES("profiles", "-pr", "For profiles", Type.PROFILES);

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
    private static final String INVALID_VALUE_MESSAGE = """
            %s is invalid value for %s flag
            
            %s""";

    private final String mappingName;
    private final String flag;
    private final String description;
    private final Type type;

    public static Schema from(String flag) {
        if (isNotAvailableInSchema(flag)) {
            throw new InvalidFlagException(withInvalidFlagMessage(flag));
        }
        return FLAG_TO_SCHEMA_MAP.get(flag);
    }

    public String invalidValueMessage(String value) {
        return INVALID_VALUE_MESSAGE.formatted(value,flag,type.getValidValueMessage());
    }

    public Object defaultValue(){
        return type.getDefaultValue();
    }

    public String regex(){
        return type.getRegex();
    }

    public Object transform(String value){
        return type.getValueTransformer().apply(value);
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
}
