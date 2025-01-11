package com.lawal.banji.springkitchen.food;

import com.lawal.banji.springkitchen.food.exception.*;
import com.lawal.banji.springkitchen.recipe.model.Step;

import java.util.Set;

public class FoodValidator {

    public static void validateFoodUpdateSource(Food food) {
        if (food == null) {
            throw new FoodUpdateSourceNullIdException(null);
        }
        if (food.getId() == null) {
            throw new FoodUpdateSourceNullIdException(food);
        }
        if (food.getName() == null || food.getName().isBlank()) {
            throw new FoodUpdateSourceNullIdException(food);
        }
        if (food.getSteps() == null) {
            throw new FoodUpdateSourceNullStepCollectionException(food);
        }
    }

    public static void validateFoodMethodIdParameter(Long id) {
        if (id == null) {
            throw new FoodMethodNullIdParameterException();
        }
    }

    public static void validateFoodMethodStringParameter(String string) {
        if (string == null || string.isBlank()) {
            throw new FoodMethodNullStringParameterException();
        }
    }

    public static void validateFoodMethodStepSetParameter(Set<Step> steps) {
        if (steps == null) {
            throw new SetFoodStepCollectionToNullException();
        }
    }

    public static void validateFoodMethodStepParameter(Step step) {
        if (step == null) {
            throw new FoodMethodNullStepParameterException();
        }
    }

    public static void validateFoodSteps(Food food) {
        if (food.getSteps() == null) {
            throw new FoodUpdateSourceNullStepCollectionException(food);
        }
    }
}
