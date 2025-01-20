package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceSaveExceptionStepCollectionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService cannot save a recipe whose step collection is null. RecipeService.save failed";

    public RecipeServiceSaveExceptionStepCollectionNull() {
        super(MESSAGE);
    }
}
