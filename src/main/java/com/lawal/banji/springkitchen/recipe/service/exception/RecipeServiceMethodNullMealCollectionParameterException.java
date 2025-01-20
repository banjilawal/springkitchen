package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceMethodNullMealCollectionParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService method does not allow null meal collection parameter";

    public RecipeServiceMethodNullMealCollectionParameterException() { super(MESSAGE); }
}
