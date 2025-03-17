package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeRepoEmptyOrNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "The RecipeService operation cannot be be conducted on an empty or null repository.";

    public RecipeRepoEmptyOrNull () {
        super( MESSAGE);
    }
}