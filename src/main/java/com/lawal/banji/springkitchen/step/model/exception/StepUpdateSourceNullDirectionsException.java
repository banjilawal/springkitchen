package com.lawal.banji.springkitchen.step.model.exception;

public class StepUpdateSourceNullDirectionsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step update Source Directions cannot be null or empty. Update failed";

    public StepUpdateSourceNullDirectionsException() {
        super(MESSAGE);
    }
}
