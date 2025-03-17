package com.lawal.banji.springkitchen.meal.model.exception;

public class NullMealFunctionParameterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Meal method does not allow a null recipe as parameter";

    public NullMealFunctionParameterException () { super(MESSAGE); }
}