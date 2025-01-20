package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceGetCollectionFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService get collection operation failed";

    public RecipeServiceGetCollectionFailed() {
        super(MESSAGE);
    }
}
