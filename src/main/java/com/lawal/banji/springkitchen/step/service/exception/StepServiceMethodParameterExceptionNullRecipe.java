package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceMethodParameterExceptionNullRecipe extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService method does not allow null Recipe parameters";

    public StepServiceMethodParameterExceptionNullRecipe() { super(MESSAGE); }
}
