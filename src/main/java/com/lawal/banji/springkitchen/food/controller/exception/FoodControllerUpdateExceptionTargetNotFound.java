package com.lawal.banji.springkitchen.food.controller.exception;

public class FoodControllerUpdateExceptionTargetNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodController. update failure No target was found with the targetId";

    public FoodControllerUpdateExceptionTargetNotFound () {
        super(MESSAGE);
    }

}