package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMethodNullMealCollectionParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method doe not allow null meal collection as parameter";

    public RecipeMethodNullMealCollectionParameterException() {
        super(MESSAGE);
    }
}
