package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceGetCollectionFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService get collection operation failed";

    public StepServiceGetCollectionFailed() {
        super(MESSAGE);
    }
}
