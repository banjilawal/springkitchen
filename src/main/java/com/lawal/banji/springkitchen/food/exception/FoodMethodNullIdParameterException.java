package com.lawal.banji.springkitchen.food.exception;

public class FoodMethodNullIdParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method does not allow null id parameters";

    public FoodMethodNullIdParameterException() { super(MESSAGE); }
}
