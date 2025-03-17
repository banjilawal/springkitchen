package com.lawal.banji.springkitchen.recipe.model.exception;

import com.lawal.banji.springkitchen.common.exception.EndTimeBeforeStartTimeToException;

public class RecipeFilterMealsByDateRangeEndTimeBeforeStartTimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = EndTimeBeforeStartTimeToException.MESSAGE + " in RecipeFilterMealsByDateRangeEndTimeBeforeStartTimeException";

    public RecipeFilterMealsByDateRangeEndTimeBeforeStartTimeException() { super(MESSAGE); }
}