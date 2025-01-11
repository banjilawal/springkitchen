package com.lawal.banji.springkitchen.food.exception;

import com.lawal.banji.springkitchen.food.Food;

public class FoodMethodNullStepCollectionParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method doe not allow null step collections";

    public FoodMethodNullStepCollectionParameterException() {
        super(MESSAGE);
    }
}
