package com.amalvadkar.ak;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Setter
@Getter
@Accessors(fluent = true)
public class Argument {
    private boolean logging;
    private boolean verbose;
    private int port;
    private String logDir = "";

    public static Argument parse(List<String> arguments) {
        Argument argument = new Argument();

        for (String arg : arguments) {
            if (isFlag(arg)) {
                int argPosition = arguments.indexOf(arg);
                Schema schema = Schema.from(arg);
                if (flagAtLastPosition(arguments, argPosition)) {
                    ReflectionUtils.setField(argument, schema.mappingName(), schema.defaultValue());
                } else {
                    String nextElement = arguments.get(argPosition + 1);
                    if (isFlag(nextElement)) {
                        ReflectionUtils.setField(argument, schema.mappingName(), schema.defaultValue());
                    } else {
                        Object flagValue = getValue(schema, nextElement);
                        ReflectionUtils.setField(argument, schema.mappingName(), flagValue);
                    }
                }
            }
        }
        return argument;
    }

    private static Object getValue(Schema schema, String value) {
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

    private static boolean flagAtLastPosition(List<String> arguments, int loggingFlagPosition) {
        return loggingFlagPosition == arguments.size() - 1;
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
