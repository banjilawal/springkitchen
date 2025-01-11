package com.lawal.banji.springkitchen.food.exception;

public class FoodMethodNullStringParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method does not allow null string parameters";

    public FoodMethodNullStringParameterException() { super(MESSAGE); }
}
