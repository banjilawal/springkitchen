package com.lawal.banji.springkitchen.common.exception;

public class NullFoodPassedToMethod extends RuntimeException {

        private static final long serialVersionUID = 1L;
        public static final String MESSAGE = "A null Food object was passed to a method";

        public NullFoodPassedToMethod() { super(MESSAGE); }
}