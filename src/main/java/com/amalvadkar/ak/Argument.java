package com.amalvadkar.ak;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public class Argument {
    private boolean logging;
    private boolean verbose;
    private int port;
}
