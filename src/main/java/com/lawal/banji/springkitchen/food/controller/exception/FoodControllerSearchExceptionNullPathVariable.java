package com.lawal.banji.springkitchen.food.controller.exception;

public class FoodControllerSearchExceptionNullPathVariable extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodController.findById search parameter cannot be null.";

    public FoodControllerSearchExceptionNullPathVariable () {
        super(MESSAGE);
    }
}