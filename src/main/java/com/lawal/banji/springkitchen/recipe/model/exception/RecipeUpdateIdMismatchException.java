package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeUpdateIdMismatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe update source.getId() does not match targetId. Update failed";

    public RecipeUpdateIdMismatchException() {
        super(MESSAGE);
    }
}
