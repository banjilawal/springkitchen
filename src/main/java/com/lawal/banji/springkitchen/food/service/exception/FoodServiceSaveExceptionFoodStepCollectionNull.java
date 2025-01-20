package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceSaveExceptionFoodStepCollectionNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService.save cannot save a food with a null collection of steps FoodService.save failed";

    public FoodServiceSaveExceptionFoodStepCollectionNull() {
        super(MESSAGE);
    }
}
