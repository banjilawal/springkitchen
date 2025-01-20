package com.lawal.banji.springkitchen.step.model.exception;

public class StepMethodNullRecipeParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step method does not allow a null recipe as parameter";

    public StepMethodNullRecipeParameterException() { super(MESSAGE); }
}
