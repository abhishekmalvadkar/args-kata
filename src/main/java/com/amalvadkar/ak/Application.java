package com.amalvadkar.ak;

import module java.base;

public class Application {
    void main(String[] args) {
        try {
            Argument argument = Argument.parse(List.of(args));
            IO.println(argument);
        } catch (InvalidFlagException | InvalidFlagValueException e) {
            IO.print(e.getMessage());
        }
    }
}
