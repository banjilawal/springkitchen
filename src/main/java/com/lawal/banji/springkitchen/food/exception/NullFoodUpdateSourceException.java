package com.lawal.banji.springkitchen.food.exception;

import com.lawal.banji.springkitchen.food.Food;

public class NullFoodUpdateSourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "Food update source cannot be null. Update failed";
    public static final String MESSAGE_TEMPLATE = "Food update '%s' source cannot be null. Update failed";

    public NullFoodUpdateSourceException(Food food) {
        super(getMessage(food));
    }

    private static String getMessage(Food food) {
        if (food == null || food.getName() == null || food.getName().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, food.getName().trim());
    }
}
