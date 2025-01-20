package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceUpdateExceptionSourceIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService update source cannot have a null id. StepService.update failed";

    public StepServiceUpdateExceptionSourceIdNull() {
        super(MESSAGE);
    }
}
