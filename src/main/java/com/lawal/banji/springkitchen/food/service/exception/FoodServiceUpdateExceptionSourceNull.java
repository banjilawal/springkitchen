package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceUpdateExceptionSourceNull extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE = "FoodService update source cannot be null. FoodService.update failed";

    public FoodServiceUpdateExceptionSourceNull() {
        super(MESSAGE);
    }
}
