package com.lawal.banji.springkitchen.common.orchestration;

import com.lawal.banji.springkitchen.common.ServiceFacade;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodServiceLoggingMessage;
import com.lawal.banji.springkitchen.food.service.FoodServiceValidator;
import com.lawal.banji.springkitchen.common.AppLogger;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceLoggingMessage;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceValidator;
import com.lawal.banji.springkitchen.recipe.service.exception.RecipeServiceCountOperationFailed;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.step.service.StepServiceLoggingMessage;
import com.lawal.banji.springkitchen.step.service.StepServiceValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.HashSet;

@Service
public class RelationshipCoordinator {

    private static final Logger logger = LoggerFactory.getLogger(RelationshipCoordinator.class);

    private final ServiceFacade serviceFacade;

    @Autowired
    public RelationshipCoordinator (ServiceFacade serviceFacade) {
        this.serviceFacade= serviceFacade;
    }

    @Transactional(readOnly = true)
    public Long getTotalRecipes() {
        AppLogger.debug(RelationshipCoordinator.class, RecipeServiceLoggingMessage.NUMBER_OF_RECIPES_MESSAGE);
        try {
            return serviceFacade.countRecipes();
        } catch (DataAccessException e) {
            AppLogger.error(
                RelationshipCoordinator.class,
                RecipeServiceCountOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long getTotalFoods() {
        AppLogger.debug(RelationshipCoordinator.class, FoodServiceLoggingMessage.NUMBER_OF_FOODS_MESSAGE);
        try {
            return serviceFacade.countFoods();
        } catch (DataAccessException e) {
            AppLogger.error(
                RelationshipCoordinator.class,
                RecipeServiceCountOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Long getTotalSteps() {
        AppLogger.debug(RelationshipCoordinator.class, StepServiceLoggingMessage.NUMBER_OF_STEPS_MESSAGE);
        try {
            return serviceFacade.countSteps();
        } catch (DataAccessException e) {
            AppLogger.error(
                RelationshipCoordinator.class,
                RecipeServiceCountOperationFailed.MESSAGE + " " + e.getMessage(), e
            );
            throw e;
        }
    }

    @Transactional
    public Recipe manageRecipeSaving(Recipe recipe) {
        RecipeServiceValidator.validateSaveRecipeParameter(recipe);
        for (Step step : recipe.getSteps()) {
            synchronizeStepFood(step, step.getIngredient());

            synchronizeRecipeStep(step, recipe);
        }
        return serviceFacade.saveRecipe(recipe);
    }

    @Transactional
    public Meal manageMealSaving(Meal meal) {
        synchronizeRecipeMeal(meal, meal.getRecipe());
        return serviceFacade.findMealById(meal.getId());
    }



    @Transactional
    public Recipe processRecipeUpdate(Long targetId, Recipe source) {
        RecipeServiceValidator.validateUpdateRecipeParameters(targetId, source);
        return manageRecipeSaving(serviceFacade.updateRecipe(targetId, source));
    }

    @Transactional
    public Food manageFoodSaving(Food food) {
        AppLogger.debug(RelationshipCoordinator.class, FoodServiceLoggingMessage.SAVING_FOOD_MESSAGE);
        FoodServiceValidator.validateSaveFoodParameter(food);
        if (food.getSteps() == null) { food.setSteps(new HashSet<>()); }
        for (Step step : food.getSteps()) {
            synchronizeRecipeStep(step, step.getRecipe());
            synchronizeStepFood(step, food);
        }
        return serviceFacade.saveFood(food);
    }

    @Transactional
    public Food processFoodUpdate(Long targetId, Food source) {
        FoodServiceValidator.validateUpdateFoodParameters(targetId, source);
        return manageFoodSaving(serviceFacade.updateFood(targetId, source));
    }

    @Transactional
    public Step manageStepSaving(Step step) {
        StepServiceValidator.validateSaveStepParameter(step);
        synchronizeRecipeStep(step, step.getRecipe());
        synchronizeStepFood(step, step.getIngredient());
        return serviceFacade.saveStep(step);
    }

    @Transactional
    public Step processStepUpdate(Long targetId, Step source) {
        StepServiceValidator.validateUpdateStepParameters(targetId, source);
        return manageStepSaving(serviceFacade.updateStep(targetId, source));
    }

    @Transactional
    public void processRecipeDeletion(Long recipeId) {
        Recipe recipe = serviceFacade.findRecipeById(recipeId);
        if (recipe == null) { return; }
        else {
            if (recipe.getSteps() != null) {
                for (Step step : recipe.getSteps()) {
                    processStepDeletion(step.getId());
                }
            }
            serviceFacade.deleteRecipeById(recipeId);
        }
    }

    @Transactional
    public void processFoodDeletion(Long foodId) {
        Food food = serviceFacade.findFoodById(foodId);
        if (food == null) { return; }
        else {
            if (food.getSteps() != null) {
                for (Step step : food.getSteps()) {
                    processStepDeletion(step.getId());
                }
            }
            serviceFacade.deleteFoodById(foodId);
        }
    }

    @Transactional
    public void processStepDeletion(Long stepId) {
        Step step = serviceFacade.findStepById(stepId);
        if (step == null) { return; }
        else {
            decoupleStepRecipe(step, step.getRecipe());
            decoupleStepFood(step, step.getIngredient());
            serviceFacade.deleteStepById(stepId);
        }
    }

    @Transactional
    public void processMealDeletion(Long mealId) {
        Meal meal = serviceFacade.findMealById(mealId);
        if (meal == null) { return; }
        else {
            decoupleMealRecipe(meal, meal.getRecipe());
            serviceFacade.deleteMealById(mealId);
        }
    }

    /* Association methods */
    private void synchronizeRecipeStep(Step step, Recipe recipe) {
        if (recipe != null && step != null) {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            recipe.addStep(step);
            step.setRecipe(recipe);
            serviceFacade.saveRecipe(recipe);
            serviceFacade.saveStep(step);
            logger.info("Associated step '{}' with recipe '{}'", step.getId(), recipe.getId());
            return;
        } if (recipe == null && step != null) {
            step.setRecipe(null);
            serviceFacade.saveStep(step);
            logger.warn("Recipe is null in associateStepWithRecipe() nothing to associate");
            return;
        } else {
            logger.error("Both recipe and step are null in associateStepWithRecipe()");
            throw new IllegalArgumentException("Recipe and step cannot both be null in associateStepWithRecipe()");
        }
    }

    private void synchronizeRecipeMeal(Meal meal, Recipe recipe) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        if (recipe != null && meal != null) {
            if (recipe.getMeals() == null) recipe.setMeals(new HashSet<>());
            recipe.addMeal(meal);
            meal.setRecipe(recipe);
            serviceFacade.saveRecipe(recipe);
            serviceFacade.saveMeal(meal);
            logger.info("{}: Associated meal'{}' with recipe '{}'", methodName, meal.getId(), recipe.getId());
        } else {
            String errorMessage = methodName + " : Error, both recipe and meal are null";

            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void synchronizeStepFood(Step step, Food food) {
        if (food != null && step != null) {
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            food.addStep(step);
            step.setIngredient(food);
            serviceFacade.saveFood(food);
            serviceFacade.saveStep(step);
            logger.info("Associated step '{}' with food '{}'", step.getId(), food.getId());
            return;
        } if (food == null && step != null) {
            step.setIngredient(null);
            serviceFacade.saveStep(step);
            logger.warn("Food is null in associateStepWithFood() nothing to associate");
            return;
        } else {
            logger.error("Both food and step are null in associateStepWithRecipe()");
            throw new IllegalArgumentException("Food and step cannot both be null in associateStepWithFood()");
        }
    }

    /* Disassociation utility methods */
    private void decoupleStepRecipe(Step step, Recipe recipe) {
        if (recipe != null && step != null) {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            recipe.removeStep(step);
            step.setRecipe(null);
            serviceFacade.saveRecipe(recipe);
            serviceFacade.saveStep(step);
            logger.info("Disassociated step '{}' from recipe '{}'", step.getId(), recipe.getId());
            return;
        } if (recipe == null && step != null) {
            step.setRecipe(null);
            serviceFacade.saveStep(step);
            logger.warn("Recipe is null in disassociateStepFromRecipe() nothing to disassociate");
        } else {
            logger.error("Both recipe and step are null in disassociateStepFromRecipe()");
            throw new IllegalArgumentException("Recipe and step cannot both be null in disassociateStepFromRecipe()");
        }
    }

    private void decoupleMealRecipe(Meal meal, Recipe recipe) {
        if (recipe != null && meal != null) {
            if (recipe.getMeals() == null) recipe.setMeals(new HashSet<>());
            recipe.removeMeal(meal);
            meal.setRecipe(null);
            serviceFacade.saveRecipe(recipe);
            logger.info("Disassociated step '{}' from recipe '{}'", meal.getId(), recipe.getId());
            return;
        } else {
            logger.error("Both recipe and step are null in disassociateStepFromRecipe()");
            throw new IllegalArgumentException("Recipe and step cannot both be null in disassociateStepFromRecipe()");
        }
    }

    private void decoupleStepFood(Step step, Food food) {
        if (food != null && step != null) {
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            food.removeStep(step);
            step.setIngredient(null);
            serviceFacade.saveFood(food);
            serviceFacade.saveStep(step);
            logger.info("Disassociated step '{}' from food '{}'", step.getId(), food.getId());
            return;
        } if (food == null && step != null) {
            step.setRecipe(null);
            serviceFacade.saveStep(step);
            logger.warn("Food is null in disassociateStepFromFood() nothing to disassociate");
        } else {
            logger.error("Both food and step are null in disassociateStepFromFood()");
            throw new IllegalArgumentException("Food and step cannot both be null in disassociateStepFromFood()");
        }
    }
}