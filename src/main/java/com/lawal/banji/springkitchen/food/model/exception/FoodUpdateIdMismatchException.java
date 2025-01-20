package com.lawal.banji.springkitchen.food.model.exception;

public class FoodUpdateIdMismatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food update source.getId() does not match targetId. Update failed";

    public FoodUpdateIdMismatchException() {
        super(MESSAGE);
    }
}
