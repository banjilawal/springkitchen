package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeStepCollectionNullxception extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe step collection cannot be null";

    public RecipeStepCollectionNullxception() { super(MESSAGE); }
}
