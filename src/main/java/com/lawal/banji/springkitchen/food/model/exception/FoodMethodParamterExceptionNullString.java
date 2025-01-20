package com.lawal.banji.springkitchen.food.model.exception;

public class FoodMethodParamterExceptionNullString extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method does not allow null string parameters";

    public FoodMethodParamterExceptionNullString() { super(MESSAGE); }
}
