package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceCountOperationFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService count operation failed";

    public FoodServiceCountOperationFailed() {
        super(MESSAGE);
    }
}
