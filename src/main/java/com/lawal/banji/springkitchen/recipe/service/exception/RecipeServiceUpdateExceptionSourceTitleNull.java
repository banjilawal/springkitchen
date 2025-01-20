package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionSourceTitleNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService update source cannot have a null or empty title. RecipeService.update failed";

    public RecipeServiceUpdateExceptionSourceTitleNull() {
        super(MESSAGE);
    }
}
