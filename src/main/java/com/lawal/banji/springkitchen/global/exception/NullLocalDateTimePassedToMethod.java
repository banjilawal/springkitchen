package com.lawal.banji.springkitchen.global.exception;

public class NullLocalDateTimePassedToMethod extends RuntimeException {

        private static final long serialVersionUID = 1L;
        public static final String MESSAGE = "A null LocalDateTime instance was passed to a method";

        public NullLocalDateTimePassedToMethod() { super(MESSAGE); }
}
