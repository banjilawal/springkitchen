package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceSaveExceptionDescriptionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService cannot save a recipe with a null or blank description. RecipeService.save failed";

    public RecipeServiceSaveExceptionDescriptionNull() {
        super(MESSAGE);
    }
}
