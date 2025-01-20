package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceSaveExceptionDirectionsNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService cannot save a step with a null or blank directions. StepService.save failed";

    public StepServiceSaveExceptionDirectionsNull() {
        super(MESSAGE);
    }
}
