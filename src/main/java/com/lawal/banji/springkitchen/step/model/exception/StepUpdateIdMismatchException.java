package com.lawal.banji.springkitchen.step.model.exception;

public class StepUpdateIdMismatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step update source.getId() does not match targetId. Update failed";

    public StepUpdateIdMismatchException() {
        super(MESSAGE);
    }
}
