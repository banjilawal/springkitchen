package com.lawal.banji.springkitchen.step.model.exception;

public class StepNullIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step id cannot be null";
    public static final String MESSAGE_TEMPLATE = "Step update Source %s annot have a null StepCollection. Update failed";

    public StepNullIdException() {
        super(MESSAGE);
    }
}
