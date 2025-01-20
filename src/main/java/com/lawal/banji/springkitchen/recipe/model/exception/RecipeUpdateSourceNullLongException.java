package com.lawal.banji.springkitchen.recipe.model.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class RecipeUpdateSourceNullLongException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe update Source Id cannot be null. Update failed";
    public static final String MESSAGE_TEMPLATE = "Recipe update Source %s Id cannot be null. Update failed";

    public RecipeUpdateSourceNullLongException() {
        super(MESSAGE);
    }
}
