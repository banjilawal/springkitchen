package com.lawal.banji.springkitchen.food.service;

import com.lawal.banji.springkitchen.food.model.*;
import com.lawal.banji.springkitchen.food.model.exception.*;
import com.lawal.banji.springkitchen.food.service.exception.*;
import com.lawal.banji.springkitchen.global.AppLogger;
import com.lawal.banji.springkitchen.step.model.Step;

import java.util.Set;

public class FoodServiceValidator {

    public static void validateSaveFoodParameter(Food food) {
        if (food == null) {
            throw new FoodServiceSaveExceptionFoodNull();
        }
        if (food.getName() == null || food.getName().isBlank()) {
            AppLogger.error(FoodServiceValidator.class, FoodServiceSaveExceptionFoodNameNull.MESSAGE + food.getName(), new FoodServiceSaveExceptionFoodNameNull());
            throw new FoodServiceSaveExceptionFoodNameNull();
        }
        if (food.getSteps() == null) {
            throw new FoodServiceSaveExceptionFoodStepCollectionNull();
        }
    }

    public static void validateUpdateFoodParameters(Long targetId, Food source) {
        if (targetId == null) {
            throw new FoodServiceUpdateExecptionTargetIdNull();
        }
        if (source == null) {
            throw new FoodServiceUpdateExceptionSourceNull();
        }
        if (source.getName() == null || source.getName().isBlank()) {
            throw new FoodServiceUpdateExceptionSourceNameNull();
        }
        if (source.getSteps() == null) {
            throw new FoodServiceUpdateExceptionSourceStepCollectionNull();
        }
//        if (source.getId() == null) {
//            throw new FoodServiceUpdateExceptionSourceIdNull();
//        }
//        if (!targetId.equals(source.getId())) {
//            throw new FoodServiceUpdateIdMismatchException();
//        }
    }

    public static void validateFoodServiceMethodLongParameter(Long number) {
        if (number == null) {
            throw new FoodServiceMethodNullLongParameterException();
        }
    }

    public static void validateFoodServiceMethodStringParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new FoodServiceMethodNullStringParameterException();
        }
    }

    public static void validateFoodServiceMethodStepSetParameter(Set<Step> steps) {
        if (steps == null) {
            throw new FoodStepsNullException();
        }
    }

    public static void validateFoodServiceMethodStepParameter(Step step) {
        if (step == null) {
            throw new FoodMethodParameterExceptionNullStep();
        }
    }
}
