package com.lawal.banji.springkitchen.food.model.exception;

public class FoodStepsNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food step collection cannot be set to null";

    public FoodStepsNullException() { super(MESSAGE); }
}
