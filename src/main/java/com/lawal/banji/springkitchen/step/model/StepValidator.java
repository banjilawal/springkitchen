package com.lawal.banji.springkitchen.step.model;

import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.step.model.exception.*;

import java.util.Set;

public class StepValidator {

    public static void validateIdMatch(Step step, Step source) {
        if (!step.getId().equals(source.getId())) {
            throw new StepUpdateIdMismatchException();
        }
    }

    public static void validateStepUpdateSource(Step source) {
        if (source == null) {
            throw new StepUpdateNullSourceException();
        }
        if (source.getId() == null) {
            throw new StepUpdateSourceNullIdException();
        }
        if (source.getDirections() == null || source.getDirections().isBlank()) {
            throw new StepUpdateSourceNullDirectionsException();
        }
    }

    public static void validateStepIngredientAmount(Double amount) {
        if (amount < 0) {
           throw new NegativeIngredientAmountException();
        }
    }

    public static void validateStepMinutes(Long minutes) {
        if (minutes != null && minutes < 0) {
            throw new NegativeTimeException();
        }
    }

    public static void validateStepMethodStringParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new StepMethodNullStringParameterException();
        }
    }

    public static void validateStepMethodRecipeParameter(Set<Step> steps) {
        if (steps == null) {
            throw new StepMethodNullRecipeParameterException();
        }
    }
}
