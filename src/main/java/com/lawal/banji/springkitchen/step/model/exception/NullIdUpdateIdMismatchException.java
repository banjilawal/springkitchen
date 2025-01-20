package com.lawal.banji.springkitchen.step.model.exception;

public class NullIdUpdateIdMismatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "NullId update source.getId() does not match targetId. Update failed";

    public NullIdUpdateIdMismatchException() {
        super(MESSAGE);
    }
}
