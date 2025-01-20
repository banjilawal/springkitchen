package com.lawal.banji.springkitchen.step.model.exception;

public class StepMethodNullLongParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step method does not allow null Long parameters";

    public StepMethodNullLongParameterException() { super(MESSAGE); }
}
