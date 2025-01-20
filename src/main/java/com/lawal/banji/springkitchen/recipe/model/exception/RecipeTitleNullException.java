package com.lawal.banji.springkitchen.recipe.model.exception;

public class RecipeTitleNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Recipe title cannot be null or blank";

    public RecipeTitleNullException() { super(MESSAGE); }
}
