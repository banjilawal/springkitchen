package com.lawal.banji.springkitchen.food.service.exception;

import com.lawal.banji.springkitchen.food.model.Food;

public class FoodServiceUpdateExceptionSourceIdNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService update source cannot have a null id. FoodService.Update failed";

    public FoodServiceUpdateExceptionSourceIdNull() {
        super(MESSAGE);
    }
}
