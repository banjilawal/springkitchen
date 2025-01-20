package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceUpdateExceptionSourceNull extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "StepService update source cannot be null. StepService.update failed";

    public StepServiceUpdateExceptionSourceNull() {
        super(MESSAGE);
    }
}
