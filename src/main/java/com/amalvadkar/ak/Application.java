package com.amalvadkar.ak;

import java.util.List;

public class Application {

	public static void main(String[] args) {
		Argument argument = Argument.parse(List.of(args));
		IO.println(argument);
	}

}
