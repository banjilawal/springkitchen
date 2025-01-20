package com.lawal.banji.springkitchen.step.service.exception;

import com.lawal.banji.springkitchen.step.model.Step;

public class StepServiceDeleteExceptionTargetNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService cannot delete an item not found. StepService.delete failed";
    public static final String MESSAGE_TEMPLATE = "Step '%s' source not saved by StepService";

    public StepServiceDeleteExceptionTargetNotFound() {
        super(MESSAGE);
    }

    private static String getMessage(Step step) {
        if (step == null || step.getDirections() == null || step.getDirections().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, step.getDirections().trim());
    }
}
