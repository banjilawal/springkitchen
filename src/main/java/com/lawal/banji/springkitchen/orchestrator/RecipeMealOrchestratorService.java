package com.lawal.banji.springkitchen.orchestrator;

import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import com.lawal.banji.springkitchen.recipe.service.IngredientService;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.recipe.service.StepService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeMealOrchestratorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeMealOrchestratorService.class);

    private final StepService stepService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final RecipeMealRelationshipManager recipeMealRelationshipManager;

    @Autowired
    public RecipeMealOrchestratorService(
        StepService stepService,
        RecipeService recipeService,
        IngredientService ingredientService,
        RecipeMealRelationshipManager recipeMealRelationshipManager
    ) {
        this.stepService = stepService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.recipeMealRelationshipManager = recipeMealRelationshipManager;
    }


    @Transactional
    public Ingredient saveIngredient(Ingredient ingredient) {
        LOGGER.info("Saving ingredient: {}", ingredient);
        return recipeMealRelationshipManager.savingIngredientHandler(ingredient);
    }

    @Transactional
    public Ingredient updateIngredient(Long targetId, Ingredient source) {
        if (targetId == null)
            throw new IllegalArgumentException(IngredientService.INGREDIENT_ID_CANNOT_BE_NULL);
        if (!ingredientService.isValidIngredient(source))
            throw new IllegalArgumentException(IngredientService.METHOD_DOES_NOT_ALLOW_NULL_INGREDIENT_AS_PARAMETER);
        Ingredient target = ingredientService.findById(targetId);
        if (target == null)
            throw new IllegalArgumentException(IngredientService.INGREDIENT_NOT_FOUND_BY_ID);
        target.getUpdate(source);
        LOGGER.info("Updated ingredient: {}", target);
        return recipeMealRelationshipManager.savingIngredientHandler(target);
    }

    @Transactional
    public void deleteIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        if (ingredient == null)
            throw new IllegalArgumentException(IngredientService.INGREDIENT_NOT_FOUND_BY_ID);
        recipeMealRelationshipManager.deletingIngredientHandler(ingredient);
        LOGGER.info("Deleting ingredient with ID: {}", ingredientId);
        ingredientService.deleteById(ingredientId);
    }

    public Ingredient findIngredientById(Long ingredientId) {
        return ingredientService.findById(ingredientId);
    }

    public List<Ingredient> findAllIngredients() { return ingredientService.findAll(); }

    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        if (!recipeService.isValidRecipe(recipe))
            throw new IllegalArgumentException(RecipeService.NULL_RECIPE_VALIDATION_FAILURE);
        LOGGER.info("Saving recipe: {}", recipe);
        return recipeMealRelationshipManager.savingRecipeHandler(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long targetId, Recipe source) {
        if (targetId == null)
            throw new IllegalArgumentException(RecipeService.RECIPE_ID_CANNOT_BE_NULL);
        if (!recipeService.isValidRecipe(source))
            throw new IllegalArgumentException(RecipeService.NULL_RECIPE_VALIDATION_FAILURE);
        Recipe target = recipeService.findById(targetId);
        if (target == null)
            throw new IllegalArgumentException(RecipeService.RECIPE_NOT_FOUND_BY_ID);
        target.getUpdate(source);
        LOGGER.info("Updated recipe: {}", target);
        return recipeMealRelationshipManager.savingRecipeHandler(target);
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        if (recipe == null)
            throw new IllegalArgumentException(RecipeService.RECIPE_NOT_FOUND_BY_ID);
        LOGGER.info("Deleting recipe with ID: {}", recipeId);
        recipeMealRelationshipManager.deletingRecipeHandler(recipe);
    }

    public Recipe findRecipeById(Long recipeId) {
        return recipeService.findById(recipeId);
    }

    public List<Recipe> findAllRecipes() { return recipeService.findAll(); }

    @Transactional
    public Step saveStep(Step step) {
        if (!stepService.isValidStep(step)) throw new IllegalArgumentException(StepService.METHOD_DOES_NOT_ALLOW_NULL_STEP_AS_PARAMETER);
        LOGGER.info("Saving step: {}", step);
        return recipeMealRelationshipManager.savingStepHandler(step);
    }

    @Transactional
    public Step updateStep(Long targetId, Step source) {
        if (targetId == null)
            throw new IllegalArgumentException(stepService.STEP_ID_CANNOT_BE_NULL);
        if (!stepService.isValidStep(source))
            throw new IllegalArgumentException(StepService.METHOD_DOES_NOT_ALLOW_NULL_STEP_AS_PARAMETER);
        Step target = stepService.findById(targetId);
        if (target == null)
            throw new IllegalArgumentException(stepService.STEP_NOT_FOUND_BY_ID);
        LOGGER.info("Updating step: {}", target);
        return recipeMealRelationshipManager.savingStepHandler(target);
    }

    @Transactional
    public void deleteStep(Long stepId) {
        Step step = stepService.findById(stepId);
        if (step == null)
            throw new IllegalArgumentException(StepService.STEP_NOT_FOUND_BY_ID);
        recipeMealRelationshipManager.deletingStepHandler(step);
        LOGGER.info("Deleting step with ID: {}", stepId);
        stepService.deleteById(stepId);
    }

    public Step findStepById(Long stepId) { return stepService.findById(stepId); }

    public List<Step> findAllSteps() { return stepService.findAll(); }

    public Set<Recipe> search(String string) {
        Set<Recipe> matches = new HashSet<>(recipeService.search(string));
        for (Step step : stepService.search(string)) {
            if (step.getRecipe() != null) { matches.add(step.getRecipe()); }
        }
        LOGGER.info("Search results for '{}': {}", string, matches.size());
        return matches;
    }

    public boolean isValidIngredient(Ingredient ingredient) { return ingredientService.isValidIngredient(ingredient); }

    public boolean isValidStep(Step step) { return stepService.isValidStep(step); }

    public boolean isValidRecipe(Recipe recipe) { return recipeService.isValidRecipe(recipe); }

    public Ingredient getRandomIngredient() { return ingredientService.randomIngredient(); }

    public Step getRandomStep() { return stepService.randomStep(); }

    public Recipe getRandomRecipe() { return recipeService.randomRecipe(); }
}
