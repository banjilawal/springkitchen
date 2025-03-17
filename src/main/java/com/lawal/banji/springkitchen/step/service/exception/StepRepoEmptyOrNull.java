package com.lawal.banji.springkitchen.step.service.exception;

public class StepRepoEmptyOrNull extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "The StepService operation cannot be be conducted on an empty or null repository.";

    public StepRepoEmptyOrNull () {
        super( MESSAGE);
    }
}