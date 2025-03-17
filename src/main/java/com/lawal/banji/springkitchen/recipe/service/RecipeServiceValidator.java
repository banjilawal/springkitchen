package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.exception.*;
import com.lawal.banji.springkitchen.recipe.service.exception.*;
import com.lawal.banji.springkitchen.step.model.Step;

import java.util.Set;

public class RecipeServiceValidator {

    public static void validateSaveRecipeParameter(Recipe recipe) {
        if (recipe == null) {
            throw new RecipeServiceSaveExceptionRecipeNull();
        }
        if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            throw new RecipeServiceSaveExceptionTitleNull();
        }
        if (recipe.getDescription() == null || recipe.getDescription().isBlank()) {
            throw new RecipeServiceSaveExceptionDescriptionNull();
        }
        if (recipe.getSteps() == null) {
            throw new RecipeServiceSaveExceptionStepCollectionNull();
        }
        if (recipe.getMeals() == null) {
            throw new RecipeServiceSaveExceptionMealCollectionNull();
        }
    }

    public static void validateUpdateRecipeParameters(Long targetId, Recipe source) {
        if (targetId == null) {
            throw new RecipeServiceUpdateExceptionTargetIdNull();
        }
//        if (source.getId() == null) {
//            throw new RecipeServiceUpdateExceptionSourceIdNull();
//        }
        if (source == null) {
            throw new RecipeServiceUpdateExceptionSourceNull();
        }
        if (source.getTitle() == null || source.getTitle().isBlank()) {
            throw new RecipeServiceUpdateExceptionSourceTitleNull();
        }
        if (source.getDescription() == null || source.getDescription().isBlank()) {
            throw new RecipeServiceUpdateExceptionSourceDescriptionNull();
        }
        if (source.getSteps() == null) {
            throw new RecipeServiceUpdateExceptionSourceStepCollectionNull();
        }
        if (source.getMeals() == null) {
            throw new RecipeServiceUpdateExceptionSourceMealCollectionNull();
        }
//        if (!targetId.equals(source.getId())) {
//            throw new RecipeServiceUpdateExceptionIdMismatch();
//        }
    }

    public static void validateRecipeServiceMethodLongParameter(Long number) {
        if (number == null) {
            throw new RecipeServiceMethodNullLongParameterException();
        }
    }

    public static void validateRecipeServiceMethodStringParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new RecipeServiceMethodNullStringParameterException();
        }
    }

    public static void validateRecipeServiceMethodStepParameter(Step step) {
        if (step == null) {
            throw new RecipeServiceMethodNullStepParameterException();
        }
    }

    public static void validateRecipeServiceMethodStepsParameter(Set<Step> steps) {
        if (steps == null) {
            throw new RecipeMethodNullStepCollectionParameterException();
        }
    }

    public static void validateRecipeServiceMethodMealParameter(Meal meal) {
        if (meal == null) {
            throw new RecipeServiceMethodNullMealParameterException();
        }
    }

    public static void validateRecipeServiceMethodRecipesParameter(Set<Meal> meals) {
        if (meals== null) {
            throw new RecipeMethodNullMealCollectionParameterException();
        }
    }

    public static void validateRecipeMeals(Recipe recipe) {
        if (recipe.getMeals() == null) {
            throw new RecipeMethodNullMealCollectionParameterException();
        }
    }
}