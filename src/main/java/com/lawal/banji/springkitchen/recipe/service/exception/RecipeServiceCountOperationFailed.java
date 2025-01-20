package com.lawal.banji.springkitchen.recipe.service.exception;

import com.lawal.banji.springkitchen.recipe.model.Recipe;

public class RecipeServiceCountOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "RecipeService count operation failed";

    public RecipeServiceCountOperationFailed() {
        super(MESSAGE);
    }
}
