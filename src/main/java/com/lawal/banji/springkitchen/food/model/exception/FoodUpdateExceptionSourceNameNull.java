package com.lawal.banji.springkitchen.food.model.exception;

public class FoodUpdateExceptionSourceNameNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Food update Source name cannot be null. Update failed";

    public FoodUpdateExceptionSourceNameNull() {
        super(MESSAGE);
    }
}
