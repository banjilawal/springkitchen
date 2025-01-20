package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMethodNullFoodParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method does not allow a null food as parameter";

    public RecipeMethodNullFoodParameterException() { super(MESSAGE); }
}
