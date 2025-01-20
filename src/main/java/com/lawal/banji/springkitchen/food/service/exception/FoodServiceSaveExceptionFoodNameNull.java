package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceSaveExceptionFoodNameNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService.save cannot save food with a null or blank name. FoodService.save failed";

    public FoodServiceSaveExceptionFoodNameNull() {
        super(MESSAGE);
    }
}
