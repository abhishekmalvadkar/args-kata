package com.amalvadkar.ak;

import java.util.List;

public class ArgumentParser {
    public static Argument parse(List<String> arguments) {
        if (arguments.contains("-l")) {
            return Argument.withLogging(true);
        }
        return null;
    }
}
