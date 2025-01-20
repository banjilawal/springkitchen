package com.lawal.banji.springkitchen.step.model.exception;

public class StepDirectionsNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step Directions cannot be null or blank";

    public StepDirectionsNullException() { super(MESSAGE); }
}
