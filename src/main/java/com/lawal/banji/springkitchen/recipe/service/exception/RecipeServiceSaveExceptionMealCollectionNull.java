package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceSaveExceptionMealCollectionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService cannot save a recipe whose meal collection is null. RecipeService.save failed";

    public RecipeServiceSaveExceptionMealCollectionNull() {
        super(MESSAGE);
    }
}
