package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMethodNullLongParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method does not allow null Long parameters";

    public RecipeMethodNullLongParameterException() { super(MESSAGE); }
}
