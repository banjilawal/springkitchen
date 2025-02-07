package com.lawal.banji.springkitchen.global;

import com.lawal.banji.springkitchen.food.controller.FoodController;
import com.lawal.banji.springkitchen.food.controller.exception.FoodControllerSearchExceptionNullPathVariable;
import com.lawal.banji.springkitchen.food.controller.exception.FoodControllerUpdateOperationFailed;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public String handleDataAccessException(DataAccessException e, Model model) {
        model.addAttribute("error", "A database error occurred: " + e.getMessage());
        return FoodController.ERROR_VIEW; // Define a common error page.
    }

    @ExceptionHandler(FoodControllerSearchExceptionNullPathVariable.class)
    public String handleNullPathVariableException(FoodControllerSearchExceptionNullPathVariable ex, Model model) {
        model.addAttribute("error", "Invalid path variable: " + ex.getMessage());
        return FoodController.ERROR_VIEW;
    }

    @ExceptionHandler(FoodControllerUpdateOperationFailed.class)
    public String handleUpdateOperationException(FoodControllerUpdateOperationFailed ex, Model model) {
        model.addAttribute("error", "Failed to update food: " + ex.getMessage());
        return FoodController.ERROR_VIEW;
    }
}