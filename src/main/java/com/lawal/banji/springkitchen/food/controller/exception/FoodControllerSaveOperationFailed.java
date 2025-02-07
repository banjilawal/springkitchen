package com.lawal.banji.springkitchen.food.controller.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class FoodControllerSaveOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food not saved by FoodController";
    public static final String MESSAGE_TEMPLATE = "Food '%s' not saved by FoodController";

    public FoodControllerSaveOperationFailed () {
        super(MESSAGE);
    }

    private static String getMessage(Food food) {
        if (food == null || food.getName() == null || food.getName().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, food.getName().trim());
    }
}