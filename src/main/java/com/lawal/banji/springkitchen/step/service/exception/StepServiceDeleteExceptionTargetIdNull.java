package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceDeleteExceptionTargetIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService cannot delete an item without the target id. StepService.delete failed";

    public StepServiceDeleteExceptionTargetIdNull() {
        super(MESSAGE);
    }
}
