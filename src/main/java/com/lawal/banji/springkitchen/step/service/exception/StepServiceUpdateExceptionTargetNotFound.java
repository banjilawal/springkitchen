package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceUpdateExceptionTargetNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update targetId cannot be null. FoodService.Update failed";

    public StepServiceUpdateExceptionTargetNotFound() {
        super(MESSAGE);
    }
}
