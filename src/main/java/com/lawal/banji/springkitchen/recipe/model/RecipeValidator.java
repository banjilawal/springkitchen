package com.lawal.banji.springkitchen.recipe.model;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.global.GlobalValidator;
import com.lawal.banji.springkitchen.global.exception.NullLocalDateTimePassedToMethod;
import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.recipe.model.exception.*;
import com.lawal.banji.springkitchen.step.model.Step;

import java.time.LocalDateTime;
import java.util.Set;

public class RecipeValidator {

    public static void validateIdMatch(Recipe recipe, Recipe source) {
        if (!recipe.getId().equals(source.getId())) {
            throw new RecipeUpdateIdMismatchException();
        }
    }

    public static void validateRecipeUpdateSource(Recipe source) {
        if (source == null) {
            throw new RecipeUpdateNullSourceException();
        }
//        if (source.getId() == null) {
//            throw new RecipeUpdateSourceNullLongException();
//        }
        if (source.getTitle() == null || source.getTitle().isBlank()) {
            throw new RecipeUpdateSourceNullTitleException();
        }
        if (source.getSteps() == null) {
            throw new RecipeUpdateSourceNullStepCollectionException();
        }
        if (source.getMeals() == null) {
            throw new RecipeUpdateSourceNullMealCollectionException();
        }
    }

    public static void validateRecipeMethodLongParameter(Long number) {
        if (number == null) {
            throw new RecipeMethodNullLongParameterException();
        }
    }

    public static void validateRecipeMethodStringParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new RecipeMethodNullStringParameterException();
        }
    }

    public static void validateRecipeMethodStepSetParameter(Set<Step> steps) {
        if (steps == null) {
            throw new SetRecipeStepCollectionToNullException();
        }
    }

    public static void validateRecipeMethodStepParameter(Step step) {
        if (step == null) {
            throw new RecipeMethodNullStepParameterException();
        }
    }

    public static void validateRecipeMethodMealSetParameter(Set<Meal> meals) {
        if (meals == null) {
            throw new RecipeMethodNullMealCollectionParameterException();
        }
    }

    public static void validateRecipeMethodMealParameter(Meal meal) {
        if (meal == null) {
            throw new RecipeMethodNullMealParameterException();
        }
    }

    public static void validateRecipeSteps(Recipe recipe) {
        if (recipe.getSteps() == null) {
            throw new RecipeUpdateSourceNullStepCollectionException();
        }
    }

    public static void validateRecipeMeals(Recipe recipe) {
        if (recipe.getMeals() == null) {
            throw new RecipeUpdateSourceNullMealCollectionException();
        }
    }

    public static void validateRecipeMethodFoodParameter(Food food) {
        GlobalValidator.validateFoodParameter(food);
    }

    public static void validateFilterMealsByDateRangeParameter(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new NullLocalDateTimePassedToMethod();
        }
        if (startDate.isAfter(endDate)) {
            throw new RecipeFilterMealsByDateRangeEndTimeBeforeStartTimeException();
        }
    }
}
