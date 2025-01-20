package com.lawal.banji.springkitchen.food.model.exception;

public class FoodMethodParameterExceptionNullStep extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method does not allow a null step";

    public FoodMethodParameterExceptionNullStep() { super(MESSAGE); }
}
