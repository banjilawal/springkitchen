package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceCountOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService count operation failed";

    public StepServiceCountOperationFailed() {
        super(MESSAGE);
    }
}
