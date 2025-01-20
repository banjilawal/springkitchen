package com.lawal.banji.springkitchen.recipe.model.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class RecipeUpdateNullSourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "Recipe update source cannot be null. Update failed";

    public RecipeUpdateNullSourceException() {
        super(MESSAGE);
    }
}
