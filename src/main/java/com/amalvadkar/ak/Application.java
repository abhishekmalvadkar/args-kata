package com.amalvadkar.ak;

import java.util.List;

public class Application {

	static void main(String[] args) {
        try {
            Argument argument = Argument.parse(List.of(args));
            IO.println(argument);
        } catch (InvalidFlagException | InvalidFlagValueException e) {
			IO.print(e.getMessage());
        }
    }

}
