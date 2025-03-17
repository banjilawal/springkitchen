package com.lawal.banji.springkitchen.step.service.exception;

import com.lawal.banji.springkitchen.step.model.Step;

public class StepServiceDeleteOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step not deleted by StepService";
    public static final String MESSAGE_TEMPLATE = "Step '%s' source not saved by StepService";

    public StepServiceDeleteOperationFailed() {
        super(MESSAGE);
    }

    private static String getMessage(Step step) {
        if (step == null || step.getInstruction() == null || step.getInstruction().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, step.getInstruction().trim());
    }
}