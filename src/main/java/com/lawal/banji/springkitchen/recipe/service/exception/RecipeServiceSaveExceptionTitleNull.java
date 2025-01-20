package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceSaveExceptionTitleNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService cannot save a recipe with a null or blank title. RecipeService.save failed";

    public RecipeServiceSaveExceptionTitleNull() {
        super(MESSAGE);
    }
}
