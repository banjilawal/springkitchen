package com.lawal.banji.springkitchen.food.controller.exception;

public class FoodControllerDeleteExceptionNullPathVariable extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodController.delete path parameter cannot be null.";

    public FoodControllerDeleteExceptionNullPathVariable () {
        super(MESSAGE);
    }
}