package com.lawal.banji.springkitchen.food.controller.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class FoodControllerSaveOperationValidationError extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodController.saveFood detected validation error. Save failed";


    public FoodControllerSaveOperationValidationError () {
        super(MESSAGE);
    }

    private static String getMessage(Food food) {
        return MESSAGE;
    }
}