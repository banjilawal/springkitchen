package com.lawal.banji.springkitchen.orchestrator;

import com.lawal.banji.springkitchen.food.Food;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeMealOrchestratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeMealOrchestratorService.class);

    private final RecipeMealRelationshipManager recipeMealRelationshipManager;

    @Autowired
    public RecipeMealOrchestratorService(RecipeMealRelationshipManager recipeMealRelationshipManager) {
        this.recipeMealRelationshipManager = recipeMealRelationshipManager;
    }

    /* Recipe Transactions */
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        LOGGER.info("Saving recipe: {}", recipe);
        return recipeMealRelationshipManager.savingRecipeHandler(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long targetId, Recipe source) {
        LOGGER.info("Updating recipe with source: {}", source);
        return recipeMealRelationshipManager.updatingRecipeHandler(targetId, source);
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        LOGGER.info("Deleting recipe with ID: {}", recipeId);
        recipeMealRelationshipManager.deletingRecipeHandler(recipeId);
    }

    /* Food Transactions */
    @Transactional
    public Food saveIngredient(Food food) {
        LOGGER.info("Saving food: {}", food);
        return recipeMealRelationshipManager.savingIngredientHandler(food);
    }

    @Transactional
    public Food updateIngredient(Long targetId, Food source) {
        LOGGER.info("Updated ingredient with source: {}", source);
        return recipeMealRelationshipManager.updatingIngredientHandler(targetId, source);
    }

    @Transactional
    public void deleteIngredient(Long ingredientId) {
        LOGGER.info("Deleting ingredient with ID: {}", ingredientId);
        recipeMealRelationshipManager.deletingIngredientHandler(ingredientId);
    }

    /* Step Transactions */
    @Transactional
    public Step savingStep(Step step) {
        LOGGER.info("Saving step: {}", step);
        return recipeMealRelationshipManager.savingStepHandler(step);
    }

    @Transactional
    public Step updatingStep(Long targetId, Step source) {
        LOGGER.info("Updating step with source: {}", source);
        return recipeMealRelationshipManager.updatingStepHandler(targetId, source);
    }

    @Transactional
    public void deleteStep(Long stepId) {
        LOGGER.info("Deleting step with ID: {}", stepId);
        recipeMealRelationshipManager.deletingStepHandler(stepId);
    }
}
