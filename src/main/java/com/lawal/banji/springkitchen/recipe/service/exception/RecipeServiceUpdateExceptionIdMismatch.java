package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionIdMismatch extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService update found targetId does not match source.getId(). RecipeService.update failed";

    public RecipeServiceUpdateExceptionIdMismatch() {
        super(MESSAGE);
    }
}
