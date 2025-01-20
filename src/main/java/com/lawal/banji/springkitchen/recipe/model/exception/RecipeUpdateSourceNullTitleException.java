package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeUpdateSourceNullTitleException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe update Source title cannot be null or empty. Update failed";

    public RecipeUpdateSourceNullTitleException() {
        super(MESSAGE);
    }
}
