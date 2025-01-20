package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceUpdateExceptionSourceStepCollectionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update source cannot have a null collection of steps. FoodService.update failed";

    public FoodServiceUpdateExceptionSourceStepCollectionNull() {
        super(MESSAGE);
    }
}
