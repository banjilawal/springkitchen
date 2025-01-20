package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeMealCollectionNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe meal collection cannot be null";

    public RecipeMealCollectionNullException() { super(MESSAGE); }
}
