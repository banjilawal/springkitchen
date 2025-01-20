package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceUpdateExceptionSourceDirectionsNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "SteppeService update source cannot have a null or empty directions. SteppeService.update failed";

    public StepServiceUpdateExceptionSourceDirectionsNull() {
        super(MESSAGE);
    }
}
