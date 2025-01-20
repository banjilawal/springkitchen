package com.lawal.banji.springkitchen.food.model;

import com.lawal.banji.springkitchen.food.model.exception.*;
import com.lawal.banji.springkitchen.step.model.Step;

import java.util.Set;

public class FoodValidator {

    public static void validateIdMatch(Food food, Food source) {
        if (!food.getId().equals(source.getId())) {
            throw new FoodUpdateIdMismatchException();
        }
    }



    public static void validateUpdatePair(Food target, Food updateSource) {
//        validateIdMatch(target, updateSource);
        validateFoodUpdateSource(updateSource);
    }

    public static void validateFoodUpdateSource(Food source) {
        if (source == null) {
            throw new FoodUpdateExceptionSourceNull();
        }
//        if (source.getId() == null) {
//            throw new FoodUpdateExceptionSourceIdNull();
//        }
        if (source.getName() == null || source.getName().isBlank()) {
            throw new FoodUpdateExceptionSourceNameNull();
        }
        if (source.getSteps() == null) {
            throw new FoodUpdateExceptionSourceStepsNull();
        }
    }

    public static void validateFoodMethodLongParameter(Long number) {
        if (number == null) {
            throw new FoodMethodParameterExceptionNullLong();
        }
    }

    public static void validateFoodMethodStringParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new FoodMethodParamterExceptionNullString();
        }
    }

    public static void validateFoodMethodStepSetParameter(Set<Step> steps) {
        if (steps == null) {
            throw new FoodStepsNullException();
        }
    }

    public static void validateFoodMethodStepParameter(Step step) {
        if (step == null) {
            throw new FoodMethodParameterExceptionNullStep();
        }
    }

    public static void validateFoodSteps(Food food) {
        if (food.getSteps() == null) {
            throw new FoodUpdateExceptionSourceStepsNull();
        }
    }
}
