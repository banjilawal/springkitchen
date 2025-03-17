package com.lawal.banji.springkitchen.common.exception;

public class EndTimeBeforeStartTimeToException extends RuntimeException {

        private static final long serialVersionUID = 1L;
        public static final String MESSAGE = "The end time is cannot be before the start time";

        public EndTimeBeforeStartTimeToException() { super(MESSAGE); }
}