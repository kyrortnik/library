package com.epam.command.exception;

public class ControllerException extends Exception {
    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }
}
