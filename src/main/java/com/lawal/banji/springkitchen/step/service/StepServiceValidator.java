package com.lawal.banji.springkitchen.step.service;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.step.service.exception.*;

public class StepServiceValidator {

    public static void validateSaveStepParameter(Step step) {
        if (step == null) {
            throw new StepServiceSaveExceptionStepNull();
        }
        if (step.getInstruction() == null || step.getInstruction().isBlank()) {
            throw new StepServiceSaveExceptionDirectionsNull();
        }
    }

    public static void validateUpdateStepParameters(Long targetId, Step source) {
        if (targetId == null) {
            throw new StepServiceUpdateExceptionTargetIdNull();
        }
        if (source == null) {
            throw new StepServiceUpdateExceptionSourceNull();
        }
        if (source.getId() == null) {
            throw new StepServiceUpdateExceptionSourceIdNull();
        }
        if (source.getInstruction() == null || source.getInstruction().isBlank()) {
            throw new StepServiceUpdateExceptionSourceDirectionsNull();
        }
        if (!targetId.equals(source.getId())) {
            throw new StepServiceUpdateExceptionIdMismatch();
        }
    }

    public static void validateLongMethodParameter(Long number) {
        if (number == null) {
            throw new StepServiceMethodParameterExceptionNullLong();
        }
    }

    public static void validateStringMethodParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new StepServiceMethodParameterExceptionNullString();
        }
    }

    public static void validateRecipeMethodParameter(Recipe recipe) {
        if (recipe == null) {
            throw new StepServiceMethodParameterExceptionNullRecipe();
        }
    }

    public static void validateIngredientMethodParameter(Food ingredient) {
        if (ingredient == null) {
            throw new StepServiceMethodParameterExceptionNullIngredient();
        }
    }
}