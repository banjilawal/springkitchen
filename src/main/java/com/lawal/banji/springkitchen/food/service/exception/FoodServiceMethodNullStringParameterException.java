package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceMethodNullStringParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService method does not allow null string parameters";

    public FoodServiceMethodNullStringParameterException() { super(MESSAGE); }
}
