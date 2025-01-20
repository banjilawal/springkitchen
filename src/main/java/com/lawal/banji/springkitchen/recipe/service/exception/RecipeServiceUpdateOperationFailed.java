package com.lawal.banji.springkitchen.recipe.service.exception;

import com.lawal.banji.springkitchen.recipe.model.Recipe;

public class RecipeServiceUpdateOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe not saved by RecipeService";
    public static final String MESSAGE_TEMPLATE = "Recipe '%s' source not saved by RecipeService";

    public RecipeServiceUpdateOperationFailed() {
        super();
    }
}
