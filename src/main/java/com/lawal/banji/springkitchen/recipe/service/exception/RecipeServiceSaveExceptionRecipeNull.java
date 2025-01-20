package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceSaveExceptionRecipeNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService cannot save a null recipe. RecipeService.save failed";

    public RecipeServiceSaveExceptionRecipeNull() {
        super(MESSAGE);
    }
}
