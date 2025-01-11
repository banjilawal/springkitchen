package com.lawal.banji.springkitchen;

public class NullIdException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Id cannot be null";

    public NullIdException() { super(MESSAGE); }
}
