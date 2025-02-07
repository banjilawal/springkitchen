package com.lawal.banji.springkitchen.food.controller.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class FoodControllerUpdateOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodController did not update the food";
    public static final String MESSAGE_TEMPLATE = "Food '%s' was not updated by FoodController.";

    public FoodControllerUpdateOperationFailed () {
        super(MESSAGE);
    }

    private static String getMessage(Food food) {
        if (food == null || food.getName() == null || food.getName().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, food.getName().trim());
    }
}