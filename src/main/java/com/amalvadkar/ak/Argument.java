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
        if (arguments.contains("-d")) {
            int logDirFlagPosition = arguments.indexOf("-d");
            if (flagAtLastPosition(arguments, logDirFlagPosition)) {
                argument.logDir("");
            } else {
                String nextElement = arguments.get(logDirFlagPosition + 1);
                if (nextElement.startsWith("-")) {
                    argument.logDir("");
                } else {
                    argument.logDir(nextElement);
                }
            }
        }
        return argument;
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
