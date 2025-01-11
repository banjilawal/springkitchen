package com.lawal.banji.springkitchen.food.exception;

import com.lawal.banji.springkitchen.food.Food;

public class FoodNameNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food Name cannot be null or blank";

    public FoodNameNullException() { super(MESSAGE); }
}
