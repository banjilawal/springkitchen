package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMethodNullMealParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method does not allow a null meal as parameter";

    public RecipeMethodNullMealParameterException() { super(MESSAGE); }
}
