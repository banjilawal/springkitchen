package com.lawal.banji.springkitchen.recipe.service.exception;

public class RecipeServiceUpdateExceptionSourceStepCollectionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService update source cannot have a null step collection. RecipeService.Update failed";

    public RecipeServiceUpdateExceptionSourceStepCollectionNull() {
        super(MESSAGE);
    }
}
