package com.lawal.banji.springkitchen.recipe.model.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class RecipeMethodNullStepParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe method does not allow a null step as parameter";

    public RecipeMethodNullStepParameterException() { super(MESSAGE); }
}
