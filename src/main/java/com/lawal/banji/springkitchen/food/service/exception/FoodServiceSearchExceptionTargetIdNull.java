package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceSearchExceptionTargetIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService.findById search parameter cannot be null.";

    public FoodServiceSearchExceptionTargetIdNull () {
        super(MESSAGE);
    }
}