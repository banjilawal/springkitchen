package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceSaveExceptionStepNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService cannot save a null step. StepService.save failed";

    public StepServiceSaveExceptionStepNull() {
        super(MESSAGE);
    }
}
