package com.lawal.banji.springkitchen.step.service.exception;

public class StepServiceMethodParameterExceptionNullIngredient extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "StepService method does not allow null Ingredient parameters";

    public StepServiceMethodParameterExceptionNullIngredient() { super(MESSAGE); }
}
