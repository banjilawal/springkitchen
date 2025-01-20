package com.lawal.banji.springkitchen.recipe.model.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class RecipeUpdateSourceNullStepCollectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe update Source cannot have a null StepCollection. Update failed";
    public static final String MESSAGE_TEMPLATE = "Recipe update Source %s annot have a null StepCollection. Update failed";

    public RecipeUpdateSourceNullStepCollectionException() {
        super(MESSAGE);
    }
}
