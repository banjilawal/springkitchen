package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceUpdateExceptionIdMismatch extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService update found targetId does not match source.getId(). StepService.update failed";

    public StepServiceUpdateExceptionIdMismatch() {
        super(MESSAGE);
    }
}
