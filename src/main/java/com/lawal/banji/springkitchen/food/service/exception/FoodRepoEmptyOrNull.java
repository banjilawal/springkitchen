package com.lawal.banji.springkitchen.food.service.exception;

public class FoodRepoEmptyOrNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "The FoodService operation cannot be be conducted on an empty or null repository.";

    public FoodRepoEmptyOrNull () {
        super( MESSAGE);
    }
}