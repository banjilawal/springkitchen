package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceUpdateExceptionTargetIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService update targetId cannot be null. StepService.update failed";

    public StepServiceUpdateExceptionTargetIdNull() {
        super(MESSAGE);
    }
}
