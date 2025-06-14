package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceMethodNullLongParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService method does not allow null Long parameters";

    public FoodServiceMethodNullLongParameterException() { super(MESSAGE); }
}
