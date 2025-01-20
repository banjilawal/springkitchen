package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionSourceIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService update source cannot have a null id. RecipeService.update failed";

    public RecipeServiceUpdateExceptionSourceIdNull() {
        super(MESSAGE);
    }
}
