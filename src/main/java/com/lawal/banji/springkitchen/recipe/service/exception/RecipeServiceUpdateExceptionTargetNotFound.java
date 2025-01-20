package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionTargetNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update targetId cannot be null. FoodService.Update failed";

    public RecipeServiceUpdateExceptionTargetNotFound() {
        super(MESSAGE);
    }
}
