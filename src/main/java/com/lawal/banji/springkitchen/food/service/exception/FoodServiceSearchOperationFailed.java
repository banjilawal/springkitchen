package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceSearchOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService search operation failed";

    public FoodServiceSearchOperationFailed () {
        super(MESSAGE);
    }
}