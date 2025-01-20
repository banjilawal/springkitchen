package com.lawal.banji.springkitchen.food.model.exception;

public class FoodMethodParameterExceptionNullStepCollection extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method doe not allow null step collections";

    public FoodMethodParameterExceptionNullStepCollection() {
        super(MESSAGE);
    }
}
