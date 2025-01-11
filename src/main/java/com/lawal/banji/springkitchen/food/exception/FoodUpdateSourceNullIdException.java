package com.lawal.banji.springkitchen.food.exception;

import com.lawal.banji.springkitchen.food.Food;

public class FoodUpdateSourceNullIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food update Source Id cannot be null. Update failed";
    public static final String MESSAGE_TEMPLATE = "Food update Source %s Id cannot be null. Update failed";

    public FoodUpdateSourceNullIdException(Food food) {
        super(getMessage(food));
    }

    private static String getMessage(Food food) {
        if (food == null || food.getName() == null || food.getName().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, food.getName().trim());
    }
}
