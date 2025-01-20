package com.lawal.banji.springkitchen.step.service.exception;



public class StepServiceSaveOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step not saved by StepService";
    public static final String MESSAGE_TEMPLATE = "Step '%s' source not saved by StepService";

    public StepServiceSaveOperationFailed() {
        super(MESSAGE);
    }
}
