package com.lawal.banji.springkitchen.orchestrator;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.food.service.FoodServiceLoggingMessage;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceCountOperationFailed;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceGetCollectionFailed;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceSaveOperationFailed;
import com.lawal.banji.springkitchen.global.AppLogger;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceLoggingMessage;
import com.lawal.banji.springkitchen.recipe.service.exception.*;
import com.lawal.banji.springkitchen.step.model.Step;

import com.lawal.banji.springkitchen.step.service.StepService;
import com.lawal.banji.springkitchen.step.service.StepServiceLoggingMessage;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceCountOperationFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceGetCollectionFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceSaveOperationFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceUpdateOperationFailed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeMealOrchestratorService {

    private final StepService stepService;
    private final FoodService foodService;
    private final RecipeService recipeService;

    private final RecipeMealServiceHelper recipeMealServiceHelper;
    private final RecipeMealRelationshipManager recipeMealRelationshipManager;

    @Autowired
    public RecipeMealOrchestratorService(
        StepService stepService,
        FoodService foodService,
        RecipeService recipeService,
        RecipeMealServiceHelper recipeMealServiceHelper,
        RecipeMealRelationshipManager recipeMealRelationshipManager
    ) {
        this.stepService = stepService;
        this.foodService = foodService;
        this.recipeService = recipeService;
        this.recipeMealServiceHelper = recipeMealServiceHelper;
        this.recipeMealRelationshipManager = recipeMealRelationshipManager;
    }

    /* Recipe Transactions */
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        AppLogger.debug(RecipeMealOrchestratorService.class, RecipeServiceLoggingMessage.SAVING_RECIPE_MESSAGE);
        try {
            return recipeMealRelationshipManager.savingRecipeHandler(recipe);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long recipeCount () {
        AppLogger.debug(RecipeMealOrchestratorService.class, RecipeServiceLoggingMessage.NUMBER_OF_RECIPES_MESSAGE);
        try {
            return recipeMealServiceHelper.countRecipes();
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceCountOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long id) {
        AppLogger.debug(RecipeMealOrchestratorService.class, RecipeServiceLoggingMessage.FINDING_RECIPE_BY_ID_MESSAGE);
        try {
            Recipe recipe = recipeMealServiceHelper.findRecipeById(id);
            AppLogger.info(RecipeMealOrchestratorService.class, RecipeServiceLoggingMessage.FOUND_RECIPE_BY_ID_MESSAGE + recipe.toString());
            return recipe;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Recipe> findAllRecipes() {
        try {
            return recipeMealServiceHelper.findAllRecipes();
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceGetCollectionFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Recipe> searchRecipes(String string) {
        try {
            return new ArrayList<Recipe>(recipeMealServiceHelper.searchRecipes(string));
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Recipe getRandomRecipe() {
        AppLogger.debug(RecipeMealOrchestratorService.class, "getRandomRecipe() called");
        try {
            Recipe recipe = recipeMealServiceHelper.getRandomRecipe();
            AppLogger.info(RecipeMealOrchestratorService.class, "getRandomRecipe() returned: " + recipe.toString());
            return recipe;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Recipe updateRecipe(Long targetId, Recipe source) {
        AppLogger.debug(RecipeMealOrchestratorService.class, RecipeServiceLoggingMessage.UPDATING_RECIPE_MESSAGE);
        try {
            return recipeMealRelationshipManager.updatingRecipeHandler(targetId, source);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        AppLogger.debug(RecipeMealOrchestratorService.class, RecipeServiceLoggingMessage.DELETING_RECIPE_MESSAGE);
        try {
            recipeMealRelationshipManager.deletingRecipeHandler(recipeId);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceDeleteOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    /* Food Transactions */
    @Transactional
    public Food saveFood(Food food) {
        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.SAVING_FOOD_MESSAGE);
        try {
            return recipeMealRelationshipManager.savingFoodHandler(food);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long foodCount () {
        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.NUMBER_OF_FOODS_MESSAGE);
        try {
            return recipeMealServiceHelper.countFoods();
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceCountOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Food findFoodById(Long id) {
        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.FINDING_FOOD_BY_ID_MESSAGE);
        try {
            Food food = recipeMealServiceHelper.findFoodById(id);
            AppLogger.info(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.FOUND_FOOD_BY_ID_MESSAGE + food.toString());
            return food;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Food findFoodByName(String name) {
        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.FINDING_FOOD_BY_NAME_MESSAGE);
        try {
            Food food = recipeMealServiceHelper.findFoodByName(name);
            AppLogger.info(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.FOUND_FOOD_BY_NAME_MESSAGE + food.toString());
            return food;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Food> searchFoods(String string) {
        try {
            return new ArrayList<Food>(recipeMealServiceHelper.searchFoods(string));
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Food> findAllFoods() {
        try {
            return recipeMealServiceHelper.findAllFoods();
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceGetCollectionFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Food getRandomFood() {
        AppLogger.debug(RecipeMealOrchestratorService.class, "getRandomFood() called");
        try {
            Food food = recipeMealServiceHelper.getRandomFood();
            AppLogger.info(RecipeMealOrchestratorService.class, "getRandomFood() returned: " + food.toString());
            return food;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Food updateFood(Long targetId, Food source) {
        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.UPDATING_FOOD_MESSAGE);
        try {
            return recipeMealRelationshipManager.updateFoodHandler(targetId, source);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }

    }

    @Transactional
    public void deleteFood(Long foodId) {
        AppLogger.debug(RecipeMealOrchestratorService.class, FoodServiceLoggingMessage.DELETING_FOOD_MESSAGE);
        try {
            recipeMealRelationshipManager.deletingFoodHandler(foodId);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    public Food generateFood () {
        return recipeMealServiceHelper.generateFood();
    }

    /* Step Transactions */
    @Transactional
    public Step saveStep(Step step) {
        AppLogger.debug(RecipeMealOrchestratorService.class, StepServiceLoggingMessage.SAVING_STEP_MESSAGE);
        try {
            return recipeMealRelationshipManager.savingStepHandler(step);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, StepServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long stepCount () {
        AppLogger.debug(RecipeMealOrchestratorService.class, StepServiceLoggingMessage.NUMBER_OF_STEPS_MESSAGE);
        try {
            return recipeMealServiceHelper.countSteps();
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, StepServiceCountOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Step findStepById(Long id) {
        AppLogger.debug(RecipeMealOrchestratorService.class, StepServiceLoggingMessage.FINDING_STEP_BY_ID_MESSAGE);
        try {
            Step step = recipeMealServiceHelper.findStepById(id);
            AppLogger.info(RecipeMealOrchestratorService.class, StepServiceLoggingMessage.FOUND_STEP_BY_ID_MESSAGE + step.toString());
            return step;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, StepServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Step> findAllSteps() {
        try {
            return recipeMealServiceHelper.findAllSteps();
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, StepServiceGetCollectionFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Step getRandomStep() {
        AppLogger.debug(RecipeMealOrchestratorService.class, "getRandomStep() called");
        try {
            Step step = recipeMealServiceHelper.getRandomStep();
            AppLogger.info(RecipeMealOrchestratorService.class, "getRandomStep() returned: " + step.toString());
            return step;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, RecipeServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Step updatingStep(Long targetId, Step source) {
        AppLogger.debug(RecipeMealOrchestratorService.class, StepServiceLoggingMessage.UPDATING_STEP_MESSAGE);
        try {
            return recipeMealRelationshipManager.updatingStepHandler(targetId, source);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, StepServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteStep(Long stepId) {
        AppLogger.debug(RecipeMealOrchestratorService.class, StepServiceLoggingMessage.DELETING_STEP_MESSAGE);
        try {
            recipeMealRelationshipManager.deletingStepHandler(stepId);
        }
        catch (DataAccessException e) {
            AppLogger.error(RecipeMealOrchestratorService.class, StepServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }
}