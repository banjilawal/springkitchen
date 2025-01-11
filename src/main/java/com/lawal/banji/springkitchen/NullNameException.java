package com.lawal.banji.springkitchen;

public class NullNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public static final String MESSAGE = "Name cannot be null or blank";

    public NullNameException() { super(MESSAGE); }
}
