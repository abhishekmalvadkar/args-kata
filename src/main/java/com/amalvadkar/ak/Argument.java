package com.amalvadkar.ak;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Accessors(fluent = true)
public class Argument {
    private boolean logging;
    private boolean verbose;
    private int port;
    private String logDir = "";
    private List<String> profiles = new ArrayList<>();

    public static Argument parse(List<String> elements) {
        Argument argument = new Argument();
        for (String currentElement : elements) {
            if (isFlag(currentElement)) {
                processFlag(elements, currentElement, argument);
            }
        }
        return argument;
    }

    private static void processFlag(List<String> elements, String flag, Argument argument) {
        Schema schema = Schema.from(flag);
        int flagPosition = elements.indexOf(flag);
        if (isLastIn(elements, flagPosition)) {
            setDefaultValue(argument, schema);
        } else {
            processIfFlagPositionIsNotLast(elements, argument, flagPosition, schema);
        }
    }

    private static void processIfFlagPositionIsNotLast(List<String> elements, Argument argument, int flagPosition, Schema schema) {
        String nextElement = elements.get(flagPosition + 1);
        if (isFlag(nextElement)) {
            setDefaultValue(argument, schema);
        } else {
            setUserDefinedFlagValue(argument, schema, nextElement);
        }
    }

    private static void setUserDefinedFlagValue(Argument argument, Schema schema, String userDefinedFlagValue) {
        Object transformedFlagValue = getTransformedFlagValueIfValid(schema, userDefinedFlagValue);
        setFlagValue(argument, schema, transformedFlagValue);
    }

    private static void setFlagValue(Argument argument, Schema schema, Object flagValue) {
        ReflectionUtils.setField(argument, schema.mappingName(), flagValue);
    }

    private static void setDefaultValue(Argument argument, Schema schema) {
        ReflectionUtils.setField(argument, schema.mappingName(), schema.defaultValue());
    }

    private static Object getTransformedFlagValueIfValid(Schema schema, String value) {
        if (isInvalidAsPer(schema, value)) {
            throw new InvalidFlagValueException(schema.invalidValueMessage(value));
        }
        return schema.transform(value);
    }

    private static boolean isInvalidAsPer(Schema schema, String value) {
        return !value.matches(schema.regex());
    }

    private static boolean isFlag(String arg) {
        return arg.startsWith("-");
    }

    private static boolean isLastIn(List<String> elements, int flagPosition) {
        return flagPosition == elements.size() - 1;
    }

    @Override
    public String toString() {
        return """
                {
                    "logging" : %s,
                    "verbose" : %s,
                    "port" : %s,
                    "directory" : "%s"
                }
                """.formatted(
                logging,
                verbose,
                port,
                logDir
        );
    }
}
