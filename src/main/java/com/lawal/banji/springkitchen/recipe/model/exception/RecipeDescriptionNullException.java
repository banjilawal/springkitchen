package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeDescriptionNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe description cannot be null or blank";

    public RecipeDescriptionNullException() { super(MESSAGE); }
}
