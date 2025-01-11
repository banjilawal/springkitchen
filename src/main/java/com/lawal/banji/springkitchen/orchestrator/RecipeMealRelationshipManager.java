package com.lawal.banji.springkitchen.orchestrator;

import com.lawal.banji.springkitchen.food.Food;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
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
        if (!recipeMealServiceHelper.isValidRecipe(recipe)) {
            throw new IllegalArgumentException("Recipe failed validation in RecipeService.save()");
        }
        if (recipe.getSteps() == null) { recipe.setSteps(new HashSet<>()); }
        for (Step step : recipe.getSteps()) {
            associateStepWithIngredient(step, step.getIngredient());
            associateStepWithRecipe(step, recipe);
        }
        return recipeMealServiceHelper.saveRecipe(recipe);
    }

    @Transactional
    public Recipe updatingRecipeHandler (Long targetId, Recipe source) {
        if (!recipeMealServiceHelper.isValidRecipeUpdate(targetId, source)) {
            logger.error("Invalid recipe passed to RecipeMealRelationshipManager: {}", source);
            throw new IllegalArgumentException("Recipe failed validation in RecipeMealRelationshipManager.");
        }
        return savingRecipeHandler(recipeMealServiceHelper.updateRecipe(targetId, source));
    }

    @Transactional
    public Food savingIngredientHandler(Food food) {
        if (recipeMealServiceHelper.isValidIngredient(food)) {
            logger.error("Invalid food passed to FoodService.save(): {}", food);
            throw new IllegalArgumentException("Food failed validation in FoodService.save()");
        }
        if (food.getSteps() == null) { food.setSteps(new HashSet<>()); }
        for (Step step : food.getSteps()) {
            associateStepWithRecipe(step, step.getRecipe());
            associateStepWithIngredient(step, food);
        }
        return recipeMealServiceHelper.saveIngredient(food);
    }

    @Transactional
    public Food updatingIngredientHandler(Long targetId, Food source) {
        if (!recipeMealServiceHelper.isValidIngredientUpdate(targetId, source)) {
            logger.error("Invalid ingredient passed to FoodService.update(): {}", source);
            throw new IllegalArgumentException("Food failed validation in FoodService.update()");
        }
        return savingIngredientHandler(recipeMealServiceHelper.updateIngredient(targetId, source));
    }

    @Transactional
    public Step savingStepHandler(Step step) {
        if (!recipeMealServiceHelper.isValidStep(step)  ) {
            logger.error("Invalid step passed to StepService.save(): {}", step);
            throw new IllegalArgumentException("Step failed validation in StepService.save()");
        }
        associateStepWithRecipe(step, step.getRecipe());
        associateStepWithIngredient(step, step.getIngredient());
        return recipeMealServiceHelper.saveStep(step);
    }

    @Transactional
    public Step updatingStepHandler(Long targetId, Step source) {
        if (!recipeMealServiceHelper.isValidStepUpdate(targetId, source)) {
            logger.error("Invalid step passed to StepService.update(): {}", source);
            throw new IllegalArgumentException("Step failed validation in StepService.update()");
        }
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
            recipeMealServiceHelper.deleteRecipe(recipeId);
        }
    }

    @Transactional
    public void deletingIngredientHandler(Long ingredientId) {
        Food food = recipeMealServiceHelper.findIngredientById(ingredientId);
        if (food == null) { return; }
        else {
            if (food.getSteps() != null) {
                for (Step step : food.getSteps()) {
                    disassociateStepFromIngredient(step, food);
                    if (step.getRecipe() != null) { recipeMealServiceHelper.saveStep(step); }
                    else { deletingStepHandler(step.getId()); }
                }
            }
            recipeMealServiceHelper.deleteIngredient(ingredientId);
        }
    }

    @Transactional
    public void deletingStepHandler(Long stepId) {
        Step step = recipeMealServiceHelper.findStepById(stepId);
        if (step == null) { return; }
        else {
            if ((step.getIngredient() != null && step.getRecipe() != null)) return;
            recipeMealServiceHelper.deleteStep(stepId);
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

    private void associateStepWithIngredient(Step step, Food food) {
        if (food != null && step != null) {
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            food.addStep(step);
            step.setIngredient(food);
            recipeMealServiceHelper.saveIngredient(food);
            recipeMealServiceHelper.saveStep(step);
            logger.info("Associated step '{}' with food '{}'", step.getId(), food.getId());
            return;
        } if (food == null && step != null) {
            step.setIngredient(null);
            recipeMealServiceHelper.saveStep(step);
            logger.warn("Food is null in associateStepWithIngredient() nothing to associate");
            return;
        } else {
            logger.error("Both food and step are null in associateStepWithRecipe()");
            throw new IllegalArgumentException("Food and step cannot both be null in associateStepWithIngredient()");
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

    private void disassociateStepFromIngredient(Step step, Food food) {
        if (food != null && step != null) {
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            food.removeStep(step);
            step.setIngredient(null);
            recipeMealServiceHelper.saveIngredient(food);
            recipeMealServiceHelper.saveStep(step);
            logger.info("Disassociated step '{}' from food '{}'", step.getId(), food.getId());
            return;
        } if (food == null && step != null) {
            step.setRecipe(null);
            recipeMealServiceHelper.saveStep(step);
            logger.warn("Food is null in disassociateStepFromIngredient() nothing to disassociate");
        } else {
            logger.error("Both food and step are null in disassociateStepFromIngredient()");
            throw new IllegalArgumentException("Food and step cannot both be null in disassociateStepFromIngredient()");
        }
    }
}
