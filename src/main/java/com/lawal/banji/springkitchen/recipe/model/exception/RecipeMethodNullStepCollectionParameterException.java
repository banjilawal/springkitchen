package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMethodNullStepCollectionParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method doe not allow null step collection as parameter";

    public RecipeMethodNullStepCollectionParameterException() {
        super(MESSAGE);
    }
}
