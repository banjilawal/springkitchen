package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeUpdateSourceNullMealCollectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe update Source cannot have a null MealCollection. Update failed";
    public static final String MESSAGE_TEMPLATE = "Recipe update Source %s annot have a null StepCollection. Update failed";

    public RecipeUpdateSourceNullMealCollectionException() {
        super(MESSAGE);
    }
}
