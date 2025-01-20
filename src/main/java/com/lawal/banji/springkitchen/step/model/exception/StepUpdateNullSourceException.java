package com.lawal.banji.springkitchen.step.model.exception;

public class StepUpdateNullSourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "Step update source cannot be null. Update failed";

    public StepUpdateNullSourceException() {
        super(MESSAGE);
    }
}
