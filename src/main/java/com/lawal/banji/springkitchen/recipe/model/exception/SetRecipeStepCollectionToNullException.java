package com.lawal.banji.springkitchen.recipe.model.exception;

public class SetRecipeStepCollectionToNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe step collection cannot be set to null";

    public SetRecipeStepCollectionToNullException() { super(MESSAGE); }
}
