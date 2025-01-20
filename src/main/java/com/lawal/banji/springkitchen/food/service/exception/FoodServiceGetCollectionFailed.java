package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceGetCollectionFailed extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService get collection operation failed";

    public FoodServiceGetCollectionFailed() {
        super(MESSAGE);
    }
}
