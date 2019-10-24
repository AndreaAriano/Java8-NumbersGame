package com.bira0003.numbersgame.Exceptions;

import java.io.IOException;

public class IOExceptionHandler extends IOException {
    public IOExceptionHandler(String message) {
        super(message);
        System.out.println("There Was an IO Error");
    }

}
