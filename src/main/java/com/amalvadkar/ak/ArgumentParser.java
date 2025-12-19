package com.amalvadkar.ak;

import java.util.List;

public class ArgumentParser {
    public static Argument parse(List<String> arguments) {
        if (arguments.contains("-l")) {
            int loggingFlagPosition = arguments.indexOf("-l");
            if (loggingFlagPosition == arguments.size() - 1) {
                return Argument.withLogging(true);
            }
            String loggingFlagValue = arguments.get(loggingFlagPosition + 1);
            return Argument.withLogging(Boolean.parseBoolean(loggingFlagValue));
        }
        return null;
    }
}
