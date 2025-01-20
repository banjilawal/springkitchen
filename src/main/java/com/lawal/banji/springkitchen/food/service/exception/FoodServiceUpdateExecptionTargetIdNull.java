package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceUpdateExecptionTargetIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update targetId cannot be null. FoodService.update failed";

    public FoodServiceUpdateExecptionTargetIdNull() {
        super(MESSAGE);
    }
}
