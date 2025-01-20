package com.lawal.banji.springkitchen.step.model.exception;

public class StepUpdateSourceNullIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step update Source Id cannot be null. Update failed";
    public static final String MESSAGE_TEMPLATE = "Step update Source %s Id cannot be null. Update failed";

    public StepUpdateSourceNullIdException() {
        super(MESSAGE);
    }
}
