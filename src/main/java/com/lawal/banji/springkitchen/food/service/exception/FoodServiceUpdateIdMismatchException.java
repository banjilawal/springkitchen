package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceUpdateIdMismatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update found targetId does not match source.getId(). FoodService.Update failed";

    public FoodServiceUpdateIdMismatchException() {
        super(MESSAGE);
    }
}
