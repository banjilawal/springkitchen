package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMethodNullStringParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method does not allow null string parameters";

    public RecipeMethodNullStringParameterException() { super(MESSAGE); }
}
