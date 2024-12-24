package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.recipe.dto.RecipeDTO;
import com.lawal.banji.springkitchen.recipe.dto.StepDTO;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class RecipeStepService {

    private final StepService stepService;
    private final RecipeService recipeService;

    @Autowired
    public RecipeStepService(StepService stepService, RecipeService recipeService) {
        this.stepService = stepService;
        this.recipeService = recipeService;
    }

    public Step saveStep(StepDTO stepDTO) {
        return stepService.save(stepDTO);
    }

    public Iterable<Step> saveAllSteps(Set<StepDTO> stepDTOs) {
        return stepService.saveAll(stepDTOs);
    }

    public Recipe saveRecipe(RecipeDTO recipeDTO) {
        return recipeService.save(recipeDTO);
    }

    public Iterable<Step> findAllSteps() {
        return stepService.findAll();
    }

    public Iterable<Recipe> findAllRecipes() {
        return recipeService.findAll();
    }

    public Step findStepById(Long id) {
        return stepService.findById(id);
    }

    public Recipe findRecipeById(Long id) {
        return recipeService.findById(id);
    }

    public void deleteByRecipeId(Long recipeId) {
        Recipe recipe = recipeService.findById(recipeId);
        if (recipe != null) {
            for (Step step : stepService.findAll()) { step.removeRecipe(recipe); }
            recipeService.deleteById(recipe.getId());
        }
    }

    public Iterable<Step> removeStepFromRecipe(Recipe recipe, Long stepId) {
        if (recipeService.findById(recipe.getId()) == null) return null;
        Step step = stepService.findById(stepId);
        if (step == null) return recipe.getSteps();
        recipe.removeStep(step);
        return recipe.getSteps();
    }

    public Iterable<Recipe> removeRecipeFromStep(Step step, Long recipeId) {
        if (stepService.findById(step.getId()) == null) return null;
        Recipe recipe = recipeService.findById(recipeId);
        if (recipe == null) return step.getRecipes();

        step.removeRecipe(recipe);
        return step.getRecipes();
    }

    public void deleteByStepId(Long stepId) {
        Step step = stepService.findById(stepId);
        if (step == null) return;
        for (Recipe recipe : recipeService.findAll() ) { recipe.removeStep(step); }
        stepService.deleteById(step.getId());
    }

    public Set<Step> findStepsByRecipeId(Long recipeId) {
        if (findRecipeById(recipeId)  == null) return null;
        Set<Step> matches = new HashSet<>();
        for (Step step : stepService.findAll() ) {
            if (step.findRecipeById(recipeId) != null) matches.add(step);
        }
        return matches;
    }

    public Set<Recipe> findRecipesByStepId(Long stepId) {
        if (findStepById(stepId) == null) return null;
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeService.findAll() ) {
            if (recipe.findStepById(stepId) != null) matches.add(recipe);
        }
        return matches;
    }

    public Step updateStep(Long targetId, StepDTO stepDTO) {
        if (findStepById(targetId) == null) return null;
        return stepService.update(targetId, stepDTO);
    }

    public Recipe updateRecipe(Long targetId, RecipeDTO recipeDTO) {
        if (findRecipeById(targetId) == null) return null;
        return recipeService.update(targetId, recipeDTO);
    }

    public Set<Recipe> search (String string) {
        Set<Recipe> recipes =  recipeService.search(string);
        for (Step step : stepService.search(string)) { recipes.addAll(step.getRecipes()); }
        return recipes;
    }

    public Set<StepDTO> stepDTOsByRecipeId (Long recipeId) {
        if (recipeId == null) return null;
        Set<StepDTO> stepDTOs = new HashSet<>();
        for (Step step : stepService.findAll()) {
            if (step.findRecipeById(recipeId) != null) stepDTOs.add(step.toDTO());
        }
        return stepDTOs;
    }

    public Set<RecipeDTO> recipeDTOsByStepId (Long stepId) {
        if (stepId == null) return null;
        Set<RecipeDTO> recipeDTOs = new HashSet<>();
        for (Recipe recipe : recipeService.findAll()) {
            recipeDTOs.add(recipe.toDTO());
        }
        return recipeDTOs;
    }
}