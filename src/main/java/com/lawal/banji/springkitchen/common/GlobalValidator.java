package com.lawal.banji.springkitchen.common;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.exception.*;
import com.lawal.banji.springkitchen.common.exception.NullIdException;
import com.lawal.banji.springkitchen.common.exception.NullNameException;
import com.lawal.banji.springkitchen.common.exception.NullStepSetException;
import com.lawal.banji.springkitchen.step.model.Step;

import java.util.Set;

public class GlobalValidator {

    public static void validateId(Long id) {
        if (id == null) {
            throw new NullIdException();
        }
    }

    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new NullNameException();
        }
    }

    public static void validateStepSet(Set<Step> steps) {
        if (steps == null) {
            throw new NullStepSetException();
        }
    }

    public static void validateFoodParameter(Food food) {
        if (food == null) {
            throw new FoodServiceSaveExceptionFoodNull();
        }
        if (food.getId() != null) {
            throw new FoodServiceUpdateExceptionSourceIdNull();
        }
        if (food.getName() == null || food.getName().isBlank()) {
            throw new FoodServiceSaveExceptionFoodNameNull();
        }
        if (food.getSteps() == null) {
            throw new FoodServiceSaveExceptionFoodStepCollectionNull();
        }
    }
}