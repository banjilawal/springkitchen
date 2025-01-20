package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionTargetIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService update targetId cannot be null. RecipeService.update failed";

    public RecipeServiceUpdateExceptionTargetIdNull() {
        super(MESSAGE);
    }
}
