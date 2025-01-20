package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceMethodNullRecipeParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService  method doe not allow null food parameter";

    public RecipeServiceMethodNullRecipeParameterException() {
        super(MESSAGE);
    }
}
