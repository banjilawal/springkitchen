package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceUpdateExceptionTargetNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService.update failed. No target was found with the targetId";

    public FoodServiceUpdateExceptionTargetNotFound() {
        super(MESSAGE);
    }

}
