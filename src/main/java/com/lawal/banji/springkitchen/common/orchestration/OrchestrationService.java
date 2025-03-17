package com.lawal.banji.springkitchen.common.orchestration;

import com.lawal.banji.springkitchen.common.RandomSelectionService;
import com.lawal.banji.springkitchen.common.ServiceFacade;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodServiceLoggingMessage;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceCountOperationFailed;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceGetCollectionFailed;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceSaveOperationFailed;
import com.lawal.banji.springkitchen.common.AppLogger;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceLoggingMessage;
import com.lawal.banji.springkitchen.recipe.service.exception.*;
import com.lawal.banji.springkitchen.step.model.Step;

import com.lawal.banji.springkitchen.step.service.StepServiceLoggingMessage;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceCountOperationFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceGetCollectionFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceSaveOperationFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceUpdateOperationFailed;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class OrchestrationService {

    private final ServiceFacade serviceFacade;
    private final RelationshipCoordinator relationshipCoordinator;

    @Autowired
    public OrchestrationService (ServiceFacade serviceFacade, RelationshipCoordinator relationshipCoordinator) {
        this.serviceFacade = serviceFacade;
        this.relationshipCoordinator = relationshipCoordinator;
    }

    /* Recipe Transactions */
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        AppLogger.debug(OrchestrationService.class, RecipeServiceLoggingMessage.SAVING_RECIPE_MESSAGE);
        try {
            return relationshipCoordinator.manageRecipeSaving(recipe);
        } catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class, RecipeServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long getRecipeCount() {
        return serviceFacade.countRecipes();
    }

    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long id) {
        return serviceFacade.findRecipeById(id);
    }

    @Transactional(readOnly = true)
    public List<Recipe> findAllRecipes() {
        return serviceFacade.findAllRecipes();
    }

    @Transactional
    public Recipe updateRecipe(Long targetId, Recipe source) {
        AppLogger.debug(OrchestrationService.class, RecipeServiceLoggingMessage.UPDATING_RECIPE_MESSAGE);
        try {
            return relationshipCoordinator.processRecipeUpdate(targetId, source);
        } catch (DataAccessException e) {
            AppLogger.error(
                OrchestrationService.class,
                RecipeServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        AppLogger.debug(OrchestrationService.class, RecipeServiceLoggingMessage.DELETING_RECIPE_MESSAGE);
        try {
            relationshipCoordinator.processRecipeDeletion(recipeId);
        } catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class, RecipeServiceDeleteOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    /* Food Transactions */
    @Transactional(readOnly = true)
    public Long getFoodCount() {
        return serviceFacade.countFoods();
    }


    @Transactional
    public Food saveFood(Food food) {
        AppLogger.debug(OrchestrationService.class, FoodServiceLoggingMessage.SAVING_FOOD_MESSAGE);
        try {
            return relationshipCoordinator.manageFoodSaving(food);
        } catch (DataAccessException e) {
            AppLogger.error(
                OrchestrationService.class,
                FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Food findFoodById(Long id) {
        return serviceFacade.findFoodById(id);
    }

    @Transactional(readOnly = true)
    public Food findFoodByName(String name) {
        return serviceFacade.findFoodByName(name);
    }

    @Transactional(readOnly = true)
    public List<Food> findAllFoods() {
        return serviceFacade.findAllFoods();
    }

    @Transactional
    public Food updateFood(Long targetId, Food source) {
        AppLogger.debug(
            OrchestrationService.class,
            FoodServiceLoggingMessage.UPDATING_FOOD_MESSAGE
        );
        try {
            return relationshipCoordinator.processFoodUpdate(targetId, source);
        } catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class,
                FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }

    }

    @Transactional
    public void deleteFood(Long foodId) {
        AppLogger.debug(OrchestrationService.class, FoodServiceLoggingMessage.DELETING_FOOD_MESSAGE);
        try {
            relationshipCoordinator.processFoodDeletion(foodId);
        } catch (DataAccessException e) {
            AppLogger.error(
                OrchestrationService.class,
                FoodServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    /* Step Transactions */
    @Transactional
    public Step saveStep(Step step) {
        AppLogger.debug(
            OrchestrationService.class,
            StepServiceLoggingMessage.SAVING_STEP_MESSAGE);
        try {
            return relationshipCoordinator.manageStepSaving(step);
        } catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class,
                StepServiceSaveOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long stepCount () {
        return serviceFacade.countSteps();
    }

    @Transactional(readOnly = true)
    public Step findStepById(Long id) {
        return serviceFacade.findStepById(id);
    }

    @Transactional(readOnly = true)
    public List<Step> findAllSteps() {
        return serviceFacade.findAllSteps();
    }

    @Transactional
    public Step updatingStep(Long targetId, Step source) {
        AppLogger.debug(OrchestrationService.class, StepServiceLoggingMessage.UPDATING_STEP_MESSAGE);
        try {
            return relationshipCoordinator.processStepUpdate(targetId, source);
        } catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class, StepServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteStep(Long stepId) {
        AppLogger.debug(OrchestrationService.class, StepServiceLoggingMessage.DELETING_STEP_MESSAGE);
        try {
            relationshipCoordinator.processStepDeletion(stepId);
        }
        catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class, StepServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    /* Meal Transactions */

    @Transactional
    public Meal saveMeal(Meal meal) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        AppLogger.debug(OrchestrationService.class, StepServiceLoggingMessage.SAVING_STEP_MESSAGE);
        try {
            return relationshipCoordinator.manageMealSaving(meal);
        } catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class,
                methodName + ": operation failed", e
            );
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public Long getMealCount() {
        return serviceFacade.countMeals();
    }

    @Transactional(readOnly = true)
    public Meal findMealById(Long id) {
        return serviceFacade.findMealById(id);
    }

    @Transactional(readOnly = true)
    public List<Meal> findAllMeals() {
        return serviceFacade.findAllMeals();
    }

    @Transactional
    public void deleteMeal(Long mealId) {
        AppLogger.debug(OrchestrationService.class, StepServiceLoggingMessage.DELETING_STEP_MESSAGE);
        try {
            relationshipCoordinator.processMealDeletion(mealId);
        }
        catch (DataAccessException e) {
            AppLogger.error(OrchestrationService.class, StepServiceUpdateOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }
}