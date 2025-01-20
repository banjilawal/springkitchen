package com.lawal.banji.springkitchen.recipe.service.exception;

import com.lawal.banji.springkitchen.recipe.model.Recipe;

public class RecipeServiceDeleteOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe not deleted by RecipeService";
    public static final String MESSAGE_TEMPLATE = "Recipe '%s' source not saved by RecipeService";

    public RecipeServiceDeleteOperationFailed() {
        super(MESSAGE);
    }

    private static String getMessage(Recipe recipe) {
        if (recipe == null || recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, recipe.getTitle().trim());
    }
}
