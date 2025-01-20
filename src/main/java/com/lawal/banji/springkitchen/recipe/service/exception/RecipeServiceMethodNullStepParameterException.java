package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceMethodNullStepParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService method does not allow null step parameter";

    public RecipeServiceMethodNullStepParameterException() { super(MESSAGE); }
}
