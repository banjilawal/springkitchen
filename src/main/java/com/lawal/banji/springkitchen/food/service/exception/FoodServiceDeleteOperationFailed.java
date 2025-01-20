package com.lawal.banji.springkitchen.food.service.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class FoodServiceDeleteOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food not deleted by FoodService";
    public static final String MESSAGE_TEMPLATE = "Food '%s' source not saved by FoodService";

    public FoodServiceDeleteOperationFailed() {
        super(MESSAGE);
    }

    private static String getMessage(Food food) {
        if (food == null || food.getName() == null || food.getName().isBlank()) {
            return MESSAGE;
        }
        return String.format(MESSAGE_TEMPLATE, food.getName().trim());
    }
}
