package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceMethodNullFoodParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService  method doe not allow null food parameter";

    public FoodServiceMethodNullFoodParameterException() {
        super(MESSAGE);
    }
}
