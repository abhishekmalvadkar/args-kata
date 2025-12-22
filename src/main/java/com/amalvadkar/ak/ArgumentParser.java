package com.amalvadkar.ak;

import java.util.List;

public class ArgumentParser {
    public static Argument parse(List<String> arguments) {
        Argument argument = new Argument();
        if (arguments.contains("-l")) {
            int loggingFlagPosition = arguments.indexOf("-l");
            if (flagAtLastPosition(arguments, loggingFlagPosition)) {
                argument.logging(true);
            } else {
                String nextElement = arguments.get(loggingFlagPosition + 1);
                if (nextElement.startsWith("-")) {
                    argument.logging(true);
                } else {
                    argument.logging(Boolean.parseBoolean(nextElement));
                }
            }
        }
        if (arguments.contains("-v")) {
            int verboseFlagPosition = arguments.indexOf("-v");
            if (flagAtLastPosition(arguments, verboseFlagPosition)) {
                argument.verbose(true);
            } else {
                String nextElement = arguments.get(verboseFlagPosition + 1);
                if (nextElement.startsWith("-")) {
                    argument.verbose(true);
                } else {
                    argument.verbose(Boolean.parseBoolean(nextElement));
                }
            }
        }
        if (arguments.contains("-p")) {
            int portFlagPosition = arguments.indexOf("-p");
            if (flagAtLastPosition(arguments, portFlagPosition)) {
                argument.port(0);
            } else {
                String nextElement = arguments.get(portFlagPosition + 1);
                if (nextElement.startsWith("-")) {
                    argument.port(0);
                } else {
                    argument.port(Integer.parseInt(nextElement));
                }
            }
        }
        return argument;
    }

    private static boolean flagAtLastPosition(List<String> arguments, int loggingFlagPosition) {
        return loggingFlagPosition == arguments.size() - 1;
    }
}
