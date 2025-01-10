package com.lawal.banji.springkitchen.orchestrator;

import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import com.lawal.banji.springkitchen.recipe.service.IngredientService;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.recipe.service.StepService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class RecipeMealRelationshipManager {

    private final StepService stepService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeMealRelationshipManager(StepService stepService, RecipeService recipeService, IngredientService ingredientService) {
        this.stepService = stepService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @Transactional
    public Recipe savingRecipeHandler (Recipe recipe) {
        if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
        for (Step step : recipe.getSteps()) {
            Ingredient ingredient = step.getIngredient();
            if (ingredient != null) {
                if (ingredient.getSteps() == null) ingredient.setSteps(new HashSet<>());
                ingredient.addStep(step);
                ingredientService.save(ingredient);
            }
            step.setRecipe(recipe);
            stepService.save(step);
        }
        return recipeService.save(recipe);
    }

    @Transactional
    public Ingredient savingIngredientHandler(Ingredient ingredient) {
        if (!ingredientService.isValidIngredient(ingredient))
            throw new IllegalArgumentException("Ingredient failed validation in IngredientService.save()");
        if (ingredient.getSteps() == null) ingredient.setSteps(new HashSet<>());
        for (Step step : ingredient.getSteps()) {
            Recipe recipe = step.getRecipe();
            if (recipe != null) {
                if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
                recipe.addStep(step);
                recipeService.save(recipe);
            }
            step.setIngredient(ingredient);
            stepService.save(step);
        }
        return ingredientService.save(ingredient);
    }

    @Transactional
    public Step savingStepHandler(Step step) {
        Recipe recipe = step.getRecipe();
        if (recipe != null) {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            recipe.addStep(step);
            recipeService.save(recipe);
        }
        Ingredient ingredient = step.getIngredient();
        if (ingredient != null) {
            if (ingredient.getSteps() == null) ingredient.setSteps(new HashSet<>());
            ingredient.addStep(step);
            ingredientService.save(ingredient);
        }
        return stepService.save(step);
    }

    @Transactional
    public void deletingRecipeHandler(Recipe recipe) {
        if (recipe.getSteps() != null) {
            for (Step step : recipe.getSteps()) {
                recipe.removeStep(step);
                if (step.getIngredient() != null) { stepService.save(step); }
                else { deletingStepHandler(step); }
            }
        }
        recipeService.deleteById(recipe.getId());
    }

    @Transactional
    public void deletingIngredientHandler(Ingredient ingredient) {
        if (ingredient.getSteps() != null) {
            for (Step step : ingredient.getSteps()) {
                ingredient.removeStep(step);
                if (step.getRecipe() != null) { stepService.save(step); }
                else { deletingStepHandler(step); }
            }
        }
        ingredientService.deleteById(ingredient.getId());
    }

    @Transactional
    public void deletingStepHandler(Step step) {
        if ((step.getIngredient() != null && step.getRecipe() != null)) return;
        stepService.deleteById(step.getId());
    }
}
