package com.lawal.banji.springkitchen.recipe;

import org.slf4j.Logger;

public class LogManager {

    public static void logAndThrow(Logger logger, String errorMessage) {
        if (logger != null) {
            logger.error(errorMessage); // Log the error
        }
        throw new IllegalArgumentException(errorMessage); // Throw the exception
    }
}
