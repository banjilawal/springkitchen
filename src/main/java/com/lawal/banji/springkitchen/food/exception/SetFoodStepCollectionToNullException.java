package com.lawal.banji.springkitchen.food.exception;

import com.lawal.banji.springkitchen.food.Food;

public class SetFoodStepCollectionToNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food step collection cannot be set to null";

    public SetFoodStepCollectionToNullException() { super(MESSAGE); }
}
