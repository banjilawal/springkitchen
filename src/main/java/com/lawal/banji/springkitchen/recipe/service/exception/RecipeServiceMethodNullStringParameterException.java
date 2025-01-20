package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceMethodNullStringParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService method does not allow null string parameters";

    public RecipeServiceMethodNullStringParameterException() { super(MESSAGE); }
}
