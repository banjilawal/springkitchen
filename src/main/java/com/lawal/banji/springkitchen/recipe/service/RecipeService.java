package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.recipe.repo.RecipeRepo;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeService {

    public static final String RECIPE_REPO_IS_EMPTY = "RecipeRepo is empty";

    public static final String RECIPE_NOT_FOUND_BY_ID = "PantryIngredientSearchService did not find item with ID %d";
    public static final String RECIPE_NOT_FOUND_BY_TITLE = "PantryIngredientSearchService did not find item with TITLE %s";
    public static final String RECIPE_NOT_FOUND_BY_DESCRIPTION = "PantryIngredientSearchService did not find item with DESCRIPTION %s";

    public static final String RECIPE_ID_CANNOT_BE_NULL = "PantryIngredientSearchService method does not accept null recipeID";
    public static final String RECIPE_FAILED_VALIDATION = "The recipe did not pass PantryIngredientSearchService validation checks";
    public static final String METHOD_DOES_NOT_ALLOW_NULL_RECIPE_AS_PARAMETER = "Null recipe cannot be passed to PantryIngredientSearchService method";

    public static final String RECIPE_UPDATE_SOURCE_FAILED_VALIDATION = "Recipe update source validation failed";
    public static final String SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE = "One or more validation checks failed. PantryIngredientSearchService.update() failed";

    public static final String RECIPE_SEARCH_QUERY_CANNOT_BE_EMPTY = "Search query cannot be null or empty";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepo recipeRepo;

    @Autowired
    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    /* Create methods */
    @Transactional
    public Recipe save (Recipe recipe) {
        if (!isValidRecipe(recipe)) loggingExceptionHandler(RECIPE_FAILED_VALIDATION);
        return recipeRepo.save(recipe);
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() { return recipeRepo.count();}

    @Transactional(readOnly = true)
    public Recipe findById(Long id) {
        if (id == null) loggingExceptionHandler(RECIPE_ID_CANNOT_BE_NULL);
        else {
            Recipe recipe = recipeRepo.findById(id).orElse(null);
            if (recipe == null) return nullRecipeLogHandler(String.format(RECIPE_NOT_FOUND_BY_ID, id));
            return recipeRepo.save(recipe);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Recipe findByTitle(String title) {
        if (title == null || title.trim().isBlank()) loggingExceptionHandler(Recipe.RECIPE_TITLE_CANNOT_BE_NULL);

        Recipe recipe = recipeRepo.findByTitleIgnoreCase(title);
        if (recipe == null) { return nullRecipeLogHandler(String.format(RECIPE_NOT_FOUND_BY_TITLE, title)); }
        else {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            if (recipe.getMeals() == null) recipe.setMeals(new HashSet<>());
            return recipeRepo.save(recipe);
        }
    }

    @Transactional(readOnly = true)
    public Recipe findByDescription(String description) {
        if (description == null || description.trim().isBlank()) loggingExceptionHandler(Recipe.RECIPE_DESCRIPTION_CANNOT_BE_NULL);

        Recipe recipe = recipeRepo.findByDescriptionIgnoreCase(description);
        if (recipe == null) { return nullRecipeLogHandler(String.format(RECIPE_NOT_FOUND_BY_TITLE, description)); }
        else {
            if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
            if (recipe.getMeals() == null) recipe.setMeals(new HashSet<>());
            return recipeRepo.save(recipe);
        }
    }

    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        return recipeRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Set<Recipe> search(String string) {
        if (string == null || string.trim().isBlank()) {
            return Collections.emptySet();
        }
        return new HashSet<>(recipeRepo.search(string));
    }

    @Transactional(readOnly = true)
    public Recipe randomRecipe() {
        long count = count();
        if (count == 0) {
            logger.info(RECIPE_REPO_IS_EMPTY + " PantryIngredientSearchService.randomRecipe() cannot return random recipe");
            return null;
        }
        return recipeRepo.findAll().get(random.nextInt((int) count));
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
        if (!areValidUpdateParameters(targetId, source)) loggingExceptionHandler(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);

        Recipe target = findById(targetId);
        if (target == null) return nullRecipeLogHandler(String.format(RECIPE_NOT_FOUND_BY_ID, targetId));

        target.getUpdate(source);
        return recipeRepo.save(target);
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        if (id == null) { loggingExceptionHandler(RECIPE_ID_CANNOT_BE_NULL); }
        else recipeRepo.deleteById(id);
    }

    /* Validation methods */
    @Transactional
    public boolean isValidRecipe(Recipe recipe) {
        if (recipe == null) {
            loggingExceptionHandler(METHOD_DOES_NOT_ALLOW_NULL_RECIPE_AS_PARAMETER);
            return false;
        }
        if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            loggingExceptionHandler(Recipe.RECIPE_TITLE_CANNOT_BE_NULL);
            return false;
        }
        if (recipe.getDescription() == null || recipe.getDescription().isBlank()) {
            loggingExceptionHandler(Recipe.RECIPE_DESCRIPTION_CANNOT_BE_NULL);
            return false;
        }
        if (recipe.getSteps() == null) recipe.setSteps(new HashSet<>());
        if (recipe.getMeals() == null) recipe.setMeals(new HashSet<>());
        return true;
    }

    @Transactional
    public boolean areValidUpdateParameters(Long targetId, Recipe source) {
        if (targetId == null || source.getId() == null) {
            loggingExceptionHandler(RECIPE_ID_CANNOT_BE_NULL);
            return false;
        }
        if (source.getId() != null && !targetId.equals(source.getId())) {
            loggingExceptionHandler(Recipe.INVALID_UPDATE_SOURCE_ID);
            return false;
        }
        if (!isValidRecipe(source)) {
            loggingExceptionHandler(RECIPE_UPDATE_SOURCE_FAILED_VALIDATION);
            return false;
        }
        if (source.getSteps() == null) {
            loggingExceptionHandler(Recipe.NULL_STEPS_COLLECTION_NOT_ALLOWED);
            return false;
        }
        if (source.getMeals() == null) {
            loggingExceptionHandler(Recipe.NULL_MEALS_COLLECTION_NOT_ALLOWED);
            return false;
        }
        return true;
    }

    /* Logging methods */
    @Transactional
    public Recipe nullRecipeLogHandler (String logMessage) {
        logger.info(logMessage);
        return null;
    }

    @Transactional
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}