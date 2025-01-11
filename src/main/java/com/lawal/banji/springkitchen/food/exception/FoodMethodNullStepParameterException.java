package com.lawal.banji.springkitchen.food.exception;

import com.lawal.banji.springkitchen.food.Food;

public class FoodMethodNullStepParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food method does not allow a null step";

    public FoodMethodNullStepParameterException() { super(MESSAGE); }

    private static String getMessage(Food food) {
        if (food == null || food.getName() == null || food.getName().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, food.getName().trim());
    }
}
