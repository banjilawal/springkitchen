package com.lawal.banji.springkitchen.step.model.exception;

public class StepMethodNullFoodParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step method does not allow a null Food as parameter";

    public StepMethodNullFoodParameterException() { super(MESSAGE); }
}
