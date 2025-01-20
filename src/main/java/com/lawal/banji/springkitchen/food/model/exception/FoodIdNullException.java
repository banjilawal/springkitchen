package com.lawal.banji.springkitchen.food.model.exception;

public class FoodIdNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food id cannot be null or blank";

    public FoodIdNullException() { super(MESSAGE); }
}
