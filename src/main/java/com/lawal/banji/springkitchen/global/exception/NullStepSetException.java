package com.lawal.banji.springkitchen.global.exception;

public class NullStepSetException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step collection cannot be null";

    public NullStepSetException() {
        super(MESSAGE);
    }
}
