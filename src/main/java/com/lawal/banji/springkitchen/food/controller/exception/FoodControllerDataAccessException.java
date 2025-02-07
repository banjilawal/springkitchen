package com.lawal.banji.springkitchen.food.controller.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class FoodControllerDataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE  = "FoodController.%s() detected data access exception.";
    public static final String MESSAGE_TEMPLATE  = "FoodController.%s() detected data access exception.";


    public FoodControllerDataAccessException (String methodName) {
        super(getMessage(methodName));
    }

    private static String getMessage(String methodName) {
        if (methodName == null || methodName.isBlank()) { return MESSAGE; }
        return String.format(MESSAGE_TEMPLATE, methodName);
    }
}