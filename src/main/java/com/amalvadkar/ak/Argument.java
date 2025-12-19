package com.amalvadkar.ak;

public class Argument {
    private final boolean logging;

    public Argument(boolean logging) {
        this.logging = logging;
    }

    public static Argument withLogging(boolean logging) {
        return new Argument(logging);
    }

    public boolean logging() {
        return logging;
    }

    public boolean verbose() {
        return false;
    }
}
