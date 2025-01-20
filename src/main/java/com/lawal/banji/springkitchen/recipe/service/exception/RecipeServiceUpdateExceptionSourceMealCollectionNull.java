package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionSourceMealCollectionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService update source cannot have a null meal collection. RecipeService.Update failed";

    public RecipeServiceUpdateExceptionSourceMealCollectionNull() {
        super(MESSAGE);
    }
}
