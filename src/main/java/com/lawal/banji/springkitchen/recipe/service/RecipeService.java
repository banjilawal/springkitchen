package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.common.AppLogger;

import com.lawal.banji.springkitchen.recipe.RecipeRepo;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.exception.RecipeServiceDeleteOperationFailed;
import com.lawal.banji.springkitchen.recipe.service.exception.RecipeServiceSaveOperationFailed;
import com.lawal.banji.springkitchen.recipe.service.exception.RecipeServiceUpdateExceptionTargetNotFound;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {

    public static final String RECIPE_NOT_FOUND_BY_ID = "PantryIngredientSearchService did not find item with ID %d";

    public static final String RECIPE_ID_CANNOT_BE_NULL = "PantryIngredientSearchService method does not accept null recipeID";

    private static final Random random = new Random();

    private final RecipeRepo recipeRepo;

    @Autowired
    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }


    /* Create methods */
    @Transactional
    public Recipe save (Recipe recipe) {
        RecipeServiceValidator.validateSaveRecipeParameter(recipe);
        AppLogger.debug(RecipeService.class, RecipeServiceLoggingMessage.SAVING_RECIPE_MESSAGE);
        try {
            Recipe savedRecipe = recipeRepo.save(recipe);
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.SUCCESSFULLY_SAVE_RECIPE + savedRecipe.toString());
            return savedRecipe;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeService.class, String.format(RecipeServiceLoggingMessage.SAVING_RECIPE_FAILED + e.getMessage()), e);
            throw e;
        }
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() {
        Long totalRecipes = recipeRepo.count();
        AppLogger.debug(RecipeService.class, totalRecipes + RecipeServiceLoggingMessage.NUMBER_OF_RECIPES_MESSAGE);
        return totalRecipes;
    }

    @Transactional(readOnly = true)
    public Recipe findById(Long id) {
        RecipeServiceValidator.validateRecipeServiceMethodLongParameter(id);
        AppLogger.debug(
            RecipeService.class,
            RecipeServiceLoggingMessage.FINDING_RECIPE_BY_ID_MESSAGE + id
        );
        Recipe recipe = recipeRepo.findById(id).orElse(null);
        if (recipe == null) {
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.RECIPE_NOT_FOUND_BY_ID_MESSAGE + id);
        } else {
            AppLogger.info(
                RecipeService.class,
                RecipeServiceLoggingMessage.FOUND_RECIPE_BY_ID_MESSAGE + recipe.toString()
            );
        }
        return recipe;
    }

    @Transactional(readOnly = true)
    public Recipe findByTitle(String title) {
        RecipeServiceValidator.validateRecipeServiceMethodStringParameter(title);
        AppLogger.debug(RecipeService.class, RecipeServiceLoggingMessage.FINDING_RECIPE_BY_TITLE_MESSAGE + title);

        Recipe recipe = recipeRepo.findByTitleIgnoreCase(title);
        if (recipe == null) {
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.RECIPE_NOT_FOUND_BY_TITLE_MESSAGE + title);
        } else {
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.FOUND_RECIPE_BY_TITLE_MESSAGE + recipe.toString());
        }
        return recipe;
    }

    @Transactional(readOnly = true)
    public Recipe findByDescription(String description) {
        RecipeServiceValidator.validateRecipeServiceMethodStringParameter(description);
        AppLogger.debug(RecipeService.class, RecipeServiceLoggingMessage.FINDING_RECIPE_BY_DESCRIPTION_MESSAGE + description);

        Recipe recipe = recipeRepo.findByDescriptionIgnoreCase(description);
        if (recipe == null) {
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.RECIPE_NOT_FOUND_BY_DESCRIPTION_MESSAGE + description);
        } else {
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.FOUND_RECIPE_BY_DESCRIPTION_MESSAGE + recipe.toString());
        }
        return recipe;
    }

    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        AppLogger.debug(RecipeService.class, RecipeServiceLoggingMessage.FETCHING_ALL_RECIPES_MESSAGE);
        List<Recipe> recipes = recipeRepo.findAll();
        AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.FOUND_ALL_RECIPES_MESSAGE + recipes.size());
        return List.copyOf(recipes);
    }

    @Transactional(readOnly = true)
    public Set<Recipe> search(String string) {
        Set<Recipe> matches = new HashSet<>();
        AppLogger.debug(RecipeService.class, RecipeServiceLoggingMessage.SEARCHING_FOR_MATCHES_BY_STRING_MESSAGE + string);
        if (string != null && !string.isBlank()) {
            string = string.toLowerCase();
            for (Recipe recipe : recipeRepo.findAll()) {
                if (recipe.getTitle().toLowerCase().contains(string) ||
                    recipe.getDescription().toLowerCase().contains(string)
                ) matches.add(recipe);
            }
        }
        AppLogger.info(RecipeService.class, matches.size() + RecipeServiceLoggingMessage.TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE + string);
        return matches;
    }

    @Transactional(readOnly = true)
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (Recipe recipe : recipeRepo.findAll()) {
            stringBuilder.append(recipe.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /* Update methods */
    @Transactional
    public Recipe update (Long targetId, Recipe source) {
        RecipeServiceValidator.validateUpdateRecipeParameters(targetId, source);
        try {
            Recipe target = findById(targetId);
            if (target == null) {
                AppLogger.error(RecipeService.class, RecipeServiceLoggingMessage.RECIPE_NOT_FOUND_BY_ID_MESSAGE + targetId, new RecipeServiceUpdateExceptionTargetNotFound());
                throw new RecipeServiceUpdateExceptionTargetNotFound();
            }
            target.getUpdate(source);
            Recipe savedRecipe = recipeRepo.save(target);
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.SUCCESSFULLY_UPDATED_RECIPE_MESSAGE + savedRecipe.toString());
            return savedRecipe;
        } catch (DataAccessException e) {
            AppLogger.error(RecipeService.class, RecipeServiceSaveOperationFailed.MESSAGE + e.getMessage(), e);
            throw e;
        }
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        RecipeServiceValidator.validateRecipeServiceMethodLongParameter(id);
        AppLogger.debug(RecipeService.class, RecipeServiceLoggingMessage.DELETING_RECIPE_MESSAGE);
        try {
            Recipe recipe = findById(id);
            if (recipe == null) return;
            recipeRepo.deleteById(id);
            AppLogger.info(RecipeService.class, RecipeServiceLoggingMessage.SUCCESSFULLY_DELETE_RECIPE);
        } catch (DataAccessException e) {
            AppLogger.error(RecipeService.class, RecipeServiceDeleteOperationFailed.MESSAGE + e.getMessage(), e);
            throw e;
        }
    }
}