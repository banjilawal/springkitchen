package com.lawal.banji.springkitchen.step.model.exception;

public class StepMethodNullStringParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step method does not allow null string parameters";

    public StepMethodNullStringParameterException() { super(MESSAGE); }
}
