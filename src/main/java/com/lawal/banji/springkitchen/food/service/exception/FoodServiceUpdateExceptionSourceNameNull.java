package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceUpdateExceptionSourceNameNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update source cannot have a null or blank name. FoodService.update failed";

    public FoodServiceUpdateExceptionSourceNameNull() {
        super(MESSAGE);
    }
}
