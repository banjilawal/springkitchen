package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionSourceNull extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "RecipeService update source cannot be null. RecipeService.update failed";

    public RecipeServiceUpdateExceptionSourceNull() {
        super(MESSAGE);
    }
}
