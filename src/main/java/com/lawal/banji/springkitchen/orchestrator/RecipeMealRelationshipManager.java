package com.lawal.banji.springkitchen.orchestrator;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodServiceLoggingMessage;
import com.lawal.banji.springkitchen.food.service.FoodServiceValidator;
import com.lawal.banji.springkitchen.global.AppLogger;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceValidator;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.step.service.StepServiceValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RecipeMealRelationshipManager {

    private static final Logger logger = LoggerFactory.getLogger(RecipeMealRelationshipManager.class);

    private final RecipeMealServiceHelper recipeMealServiceHelper;

    @Autowired
    public RecipeMealRelationshipManager(RecipeMealServiceHelper recipeMealServiceHelper ) {
        this.recipeMealServiceHelper = recipeMealServiceHelper;
    }

    @Transactional
    public Recipe savingRecipeHandler (Recipe recipe) {
        RecipeServiceValidator.validateSaveRecipeParameter(recipe);
        for (Step step : recipe.getSteps()) {
            associateStepWithFood(step, step.getIngredient());
            associateStepWithRecipe(step, recipe);
        }
        return recipeMealServiceHelper.saveRecipe(recipe);
    }

    @Transactional
    public Recipe updatingRecipeHandler (Long targetId, Recipe source) {
        RecipeServiceValidator.validateUpdateRecipeParameters(targetId, source);
        return savingRecipeHandler(recipeMealServiceHelper.updateRecipe(targetId, source));
    }

    @Transactional
    public Food savingFoodHandler(Food food) {
        AppLogger.debug(RecipeMealRelationshipManager.class, FoodServiceLoggingMessage.SAVING_FOOD_MESSAGE);
        FoodServiceValidator.validateSaveFoodParameter(food);
        if (food.getSteps() == null) { food.setSteps(new HashSet<>()); }
        for (Step step : food.getSteps()) {
            associateStepWithRecipe(step, step.getRecipe());
            associateStepWithFood(step, food);
        }
        return recipeMealServiceHelper.saveFood(food);
    }

    @Transactional
    public Food updateFoodHandler(Long targetId, Food source) {
        FoodServiceValidator.validateUpdateFoodParameters(targetId, source);
        return savingFoodHandler(recipeMealServiceHelper.updateFood(targetId, source));
    }

    @Transactional
    public Step savingStepHandler(Step step) {
        StepServiceValidator.validateSaveStepParameter(step);
        associateStepWithRecipe(step, step.getRecipe());
        associateStepWithFood(step, step.getIngredient());
        return recipeMealServiceHelper.saveStep(step);
    }

    @Transactional
    public Step updatingStepHandler(Long targetId, Step source) {
        StepServiceValidator.validateUpdateStepParameters(targetId, source);
        return savingStepHandler(recipeMealServiceHelper.updateStep(targetId, source));
    }

    @Transactional
    public void deletingRecipeHandler(Long recipeId) {
        Recipe recipe = recipeMealServiceHelper.findRecipeById(recipeId);
        if (recipe == null) { return; }
        else {
            if (recipe.getSteps() != null) {
                for (Step step : recipe.getSteps()) {
                    disassociateStepFromRecipe(step, recipe);
                    if (step.getIngredient() != null) { recipeMealServiceHelper.saveStep(step); }
                    else { deletingStepHandler(step.getId()); }
                }
            }
            recipeMealServiceHelper.deleteRecipeById(recipeId);
        }
    }

    @Transactional
    public void deletingFoodHandler(Long foodId) {
        Food food = recipeMealServiceHelper.findFoodById(foodId);
        if (food == null) { return; }
        else {
            if (food.getSteps() != null) {
                for (Step step : food.getSteps()) {
                    disassociateStepFromFood(step, food);
                    if (step.getRecipe() != null) { recipeMealServiceHelper.saveStep(step); }
                    else { deletingStepHandler(step.getId()); }
                }
            }
            recipeMealServiceHelper.deleteFoodById(foodId);
        }
    }

    @Transactional
    public void deletingStepHandler(Long stepId) {
        Step step = recipeMealServiceHelper.findStepById(stepId);
        if (step == null) { return; }
        else {
            disassociateStepFromRecipe(step, step.getRecipe());
            disassociateStepFromFood(step, step.getIngredient());
            recipeMealServiceHelper.deleteStepById(stepId);
        }
    }

    /* Association methods */
    private void associateStepWithRecipe(Step step, Recipe recipe) {
        if (recipe != null && step != null) {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            recipe.addStep(step);
            step.setRecipe(recipe);
            recipeMealServiceHelper.saveRecipe(recipe);
            recipeMealServiceHelper.saveStep(step);
            logger.info("Associated step '{}' with recipe '{}'", step.getId(), recipe.getId());
            return;
        } if (recipe == null && step != null) {
            step.setRecipe(null);
            recipeMealServiceHelper.saveStep(step);
            logger.warn("Recipe is null in associateStepWithRecipe() nothing to associate");
            return;
        } else {
            logger.error("Both recipe and step are null in associateStepWithRecipe()");
            throw new IllegalArgumentException("Recipe and step cannot both be null in associateStepWithRecipe()");
        }
    }

    private void associateStepWithFood(Step step, Food food) {
        if (food != null && step != null) {
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            food.addStep(step);
            step.setIngredient(food);
            recipeMealServiceHelper.saveFood(food);
            recipeMealServiceHelper.saveStep(step);
            logger.info("Associated step '{}' with food '{}'", step.getId(), food.getId());
            return;
        } if (food == null && step != null) {
            step.setIngredient(null);
            recipeMealServiceHelper.saveStep(step);
            logger.warn("Food is null in associateStepWithFood() nothing to associate");
            return;
        } else {
            logger.error("Both food and step are null in associateStepWithRecipe()");
            throw new IllegalArgumentException("Food and step cannot both be null in associateStepWithFood()");
        }
    }

    /* Disassociation utility methods */
    private void disassociateStepFromRecipe(Step step, Recipe recipe) {
        if (recipe != null && step != null) {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            recipe.removeStep(step);
            step.setRecipe(null);
            recipeMealServiceHelper.saveRecipe(recipe);
            recipeMealServiceHelper.saveStep(step);
            logger.info("Disassociated step '{}' from recipe '{}'", step.getId(), recipe.getId());
            return;
        } if (recipe == null && step != null) {
            step.setRecipe(null);
            recipeMealServiceHelper.saveStep(step);
            logger.warn("Recipe is null in disassociateStepFromRecipe() nothing to disassociate");
        } else {
            logger.error("Both recipe and step are null in disassociateStepFromRecipe()");
            throw new IllegalArgumentException("Recipe and step cannot both be null in disassociateStepFromRecipe()");
        }
    }

    private void disassociateStepFromFood(Step step, Food food) {
        if (food != null && step != null) {
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            food.removeStep(step);
            step.setIngredient(null);
            recipeMealServiceHelper.saveFood(food);
            recipeMealServiceHelper.saveStep(step);
            logger.info("Disassociated step '{}' from food '{}'", step.getId(), food.getId());
            return;
        } if (food == null && step != null) {
            step.setRecipe(null);
            recipeMealServiceHelper.saveStep(step);
            logger.warn("Food is null in disassociateStepFromFood() nothing to disassociate");
        } else {
            logger.error("Both food and step are null in disassociateStepFromFood()");
            throw new IllegalArgumentException("Food and step cannot both be null in disassociateStepFromFood()");
        }
    }
}
