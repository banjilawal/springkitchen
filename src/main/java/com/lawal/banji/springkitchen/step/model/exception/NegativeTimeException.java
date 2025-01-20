package com.lawal.banji.springkitchen.step.model.exception;

public class NegativeTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step does not allow negative time";

    public NegativeTimeException() {
        super(MESSAGE);
    }
}
