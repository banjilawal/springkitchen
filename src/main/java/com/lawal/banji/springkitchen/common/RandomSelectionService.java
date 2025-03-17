package com.lawal.banji.springkitchen.common;


import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.food.service.FoodServiceLoggingMessage;
import com.lawal.banji.springkitchen.food.service.exception.FoodRepoEmptyOrNull;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.meal.service.MealService;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceLoggingMessage;
import com.lawal.banji.springkitchen.recipe.service.exception.RecipeRepoEmptyOrNull;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.step.service.StepService;
import com.lawal.banji.springkitchen.step.service.StepServiceLoggingMessage;
import com.lawal.banji.springkitchen.step.service.exception.StepRepoEmptyOrNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class RandomSelectionService {

    private final FoodService foodService;
    private final RecipeService recipeService;
    private final StepService stepService;
    private final MealService mealService;

    @Autowired
    public RandomSelectionService (FoodService foodService, RecipeService recipeService, StepService stepService, MealService mealService) {
        this.foodService = foodService;
        this.recipeService = recipeService;
        this.stepService = stepService;
        this.mealService = mealService;
    }

    @Transactional(readOnly = true)
    public Food randomFood() {
        AppLogger.debug(RandomSelectionService.class, FoodServiceLoggingMessage.RANDOMLY_SELECTING_FOOD_MESSAGE);
        int numberOfFoods = foodService.count().intValue();

        if (numberOfFoods == 0) {
            AppLogger.error(RandomSelectionService.class, FoodRepoEmptyOrNull.MESSAGE, new FoodRepoEmptyOrNull());
            throw new FoodRepoEmptyOrNull();
        }

        int index = 0;
        int number = new Random().nextInt(numberOfFoods);

        for (Food food : foodService.findAll()) {
            if (index == number) {
                AppLogger.info(
                    RandomSelectionService.class,
                    FoodServiceLoggingMessage.RANDOMLY_SELECTING_FOOD_MESSAGE + food.toString()
                );
                return food;
            }
            index++;
        }
        AppLogger.debug(
            FoodService.class,
            FoodServiceLoggingMessage.FAILED_TO_GET_RANDOM_FOOD_FROM_NONEMPTY_REPO_MESSAGE
        );
        return null;
    }

    @Transactional(readOnly = true)
    public Recipe randomRecipe() {
        AppLogger.debug(RandomSelectionService.class, RecipeServiceLoggingMessage.RANDOMLY_SELECTING_RECIPE_MESSAGE);
        int numberOfRecipes = recipeService.count().intValue();

        if (numberOfRecipes == 0) {
            AppLogger.error(
                RandomSelectionService.class,
                RecipeRepoEmptyOrNull.MESSAGE, new RecipeRepoEmptyOrNull()
            );
            throw new RecipeRepoEmptyOrNull();
        }

        int index = 0;
        int number = new Random().nextInt(numberOfRecipes);

        for (Recipe recipe : recipeService.findAll()) {
            if (index == number) {
                AppLogger.info(
                    RandomSelectionService.class,
                    RecipeServiceLoggingMessage.RANDOMLY_SELECTING_RECIPE_MESSAGE+ recipe.toString()
                );
                return recipe;
            }
            index++;
        }
        AppLogger.debug(
            RandomSelectionService.class,
            RecipeServiceLoggingMessage.FAILED_TO_GET_RANDOM_RECIPE_FROM_NONEMPTY_REPO_MESSAGE
        );
        return null;
    }

    @Transactional(readOnly = true)
    public Meal randomMeal() {
        AppLogger.debug(RandomSelectionService.class, RecipeServiceLoggingMessage.RANDOMLY_SELECTING_RECIPE_MESSAGE);
        int numberOfMeals = mealService.count().intValue();

        if (numberOfMeals == 0) {
            AppLogger.error(
                RandomSelectionService.class,
                RecipeRepoEmptyOrNull.MESSAGE, new RecipeRepoEmptyOrNull()
            );
            throw new RecipeRepoEmptyOrNull();
        }

        int index = 0;
        int number = new Random().nextInt(numberOfMeals);

        for (Meal meal : mealService.findAll()) {
            if (index == number) {
                AppLogger.info(
                    RandomSelectionService.class,
                    RecipeServiceLoggingMessage.RANDOMLY_SELECTING_RECIPE_MESSAGE+ meal.toString()
                );
                return meal;
            }
            index++;
        }
        AppLogger.debug(
            RandomSelectionService.class,
            RecipeServiceLoggingMessage.FAILED_TO_GET_RANDOM_RECIPE_FROM_NONEMPTY_REPO_MESSAGE
        );
        return null;
    }

    @Transactional(readOnly = true)
    public Step randomStep() {

        AppLogger.debug(RandomSelectionService.class, StepServiceLoggingMessage.RANDOMLY_SELECTING_STEP_MESSAGE);
        int numberOfSteps = stepService.count().intValue();

        if (numberOfSteps == 0) {
            AppLogger.error(RandomSelectionService.class, StepRepoEmptyOrNull.MESSAGE, new StepRepoEmptyOrNull());
            throw new StepRepoEmptyOrNull();
        }

        int index = 0;
        int number = new Random().nextInt(numberOfSteps);

        for (Step step : stepService.findAll()) {
            if (index == number) {
                AppLogger.info(
                    RandomSelectionService.class,
                    StepServiceLoggingMessage.RANDOMLY_SELECTING_STEP_MESSAGE + step.toString());
                return step;
            }
            index++;
        }
        AppLogger.debug(
            RandomSelectionService.class,
            StepServiceLoggingMessage.FAILED_TO_GET_RANDOM_STEP_FROM_NONEMPTY_REPO_MESSAGE
        );
        return null;
    }
}