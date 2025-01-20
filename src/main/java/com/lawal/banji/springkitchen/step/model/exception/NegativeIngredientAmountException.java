package com.lawal.banji.springkitchen.step.model.exception;

public class NegativeIngredientAmountException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Step does not allow negative ingredient amount";

    public NegativeIngredientAmountException() {
        super(MESSAGE);
    }
}
