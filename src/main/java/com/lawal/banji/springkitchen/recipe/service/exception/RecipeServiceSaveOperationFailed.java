package com.lawal.banji.springkitchen.recipe.service.exception;

import com.lawal.banji.springkitchen.recipe.model.Recipe;

public class RecipeServiceSaveOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe not saved by RecipeService";
    public static final String MESSAGE_TEMPLATE = "Recipe '%s' source not saved by RecipeService";

    public RecipeServiceSaveOperationFailed() {
        super(MESSAGE);
    }

    private static String getMessage(Recipe recipe) {
        if (recipe == null || recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, recipe.getTitle().trim());
    }
}
