package com.lawal.banji.springkitchen.food.service.exception;

public class FoodServiceSearchResultNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "FoodService did not find anything with matching criteria";

    public FoodServiceSearchResultNull () {
        super(MESSAGE);
    }
}