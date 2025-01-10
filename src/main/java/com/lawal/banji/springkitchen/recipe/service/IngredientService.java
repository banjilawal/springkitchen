package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import com.lawal.banji.springkitchen.recipe.repo.IngredientRepo;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IngredientService {

    public static final String INGREDIENT_REPO_IS_EMPTY = "IngredientRepo is empty";

    public static final String INGREDIENT_NOT_FOUND_BY_ID = "IngredientService did not find item with ID %d";
    public static final String INGREDIENT_NOT_FOUND_BY_NAME = "IngredientService did not find item with NAME %s";

    public static final String INGREDIENT_ID_CANNOT_BE_NULL = "IngredientService method does not accept null ingredientID";
    public static final String INGREDIENT_FAILED_VALIDATION = "The ingredient did not pass IngredientService validation checks";

    public static final String INGREDIENT_UPDATE_SOURCE_FAILED_VALIDATION = "Ingredient update source validation failed";
    public static final String SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE = "ONe or more validation checks failed. IngredientService.update() failed";
    public static final String METHOD_DOES_NOT_ALLOW_NULL_INGREDIENT_AS_PARAMETER = "Null ingredient cannot be passed to IngredientService method";

    public static final String INGREDIENT_SEARCH_QUERY_CANNOT_BE_EMPTY = "Search query cannot be null or empty";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepo ingredientRepo;

    @Autowired
    public IngredientService(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    /* Create methods */
    @Transactional
    public Ingredient save (Ingredient ingredient) {
        if (!isValidIngredient(ingredient)) loggingExceptionHandler(INGREDIENT_FAILED_VALIDATION);
        return ingredientRepo.save(ingredient);
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() { return ingredientRepo.count();}

    @Transactional(readOnly = true)
    public Ingredient findById(Long id) {
        if (id == null) loggingExceptionHandler(INGREDIENT_ID_CANNOT_BE_NULL);
        else {
            Ingredient ingredient = ingredientRepo.findById(id).orElse(null);
            if (ingredient == null) return nullIngredientLogHandler(String.format(INGREDIENT_NOT_FOUND_BY_ID, id));
            return ingredientRepo.save(ingredient);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Ingredient findByName(String name) {
        if (name == null || name.isBlank()) loggingExceptionHandler(Ingredient.INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);

        Ingredient ingredient = ingredientRepo.findByNameIgnoreCase(name);
        if (ingredient == null) { return nullIngredientLogHandler(String.format(INGREDIENT_NOT_FOUND_BY_NAME, name)); }
        else {
            if (ingredient.getSteps() == null) ingredient.setSteps(new HashSet<>());
            return ingredientRepo.save(ingredient);
        }
    }

    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        return ingredientRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Set<Ingredient> search(String string) {
        if (string == null || string.trim().isBlank()) {
            return Collections.emptySet();
        }
        return new HashSet<>(ingredientRepo.findByNameContainingIgnoreCase(string));
    }

    @Transactional(readOnly = true)
    public Ingredient randomIngredient() {
        long count = count();
        if (count == 0) {
            logger.info(INGREDIENT_REPO_IS_EMPTY + " IngredientService.randomIngredient() cannot return random ingredient");
            return null;
        }
        return ingredientRepo.findAll().get(random.nextInt((int) count));
    }

    @Transactional(readOnly = true)
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient ingredient : ingredientRepo.findAll()) {
            stringBuilder.append(ingredient.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /* Update methods */
    @Transactional
    public Ingredient update (Long targetId, Ingredient source) {
        if (!areValidUpdateParameters(targetId, source)) loggingExceptionHandler(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);

        Ingredient target = findById(targetId);
        if (target == null) return nullIngredientLogHandler(String.format(INGREDIENT_NOT_FOUND_BY_ID, targetId));

        target.getUpdate(source);
        return ingredientRepo.save(target);
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        if (id == null) { loggingExceptionHandler(INGREDIENT_ID_CANNOT_BE_NULL); }
        else ingredientRepo.deleteById(id);
    }

    /* Validation methods */
    @Transactional
    public boolean isValidIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            loggingExceptionHandler(METHOD_DOES_NOT_ALLOW_NULL_INGREDIENT_AS_PARAMETER);
            return false;
        }
        if (ingredient.getName() == null || ingredient.getName().trim().isBlank()) {
            loggingExceptionHandler(Ingredient.INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);
            return false;
        }
        if (ingredient.getSteps() == null) ingredient.setSteps(new HashSet<>());
        return true;
    }

    @Transactional
    public boolean areValidUpdateParameters(Long targetId, Ingredient source) {
        if (targetId == null || source.getId() == null) {
            loggingExceptionHandler(INGREDIENT_ID_CANNOT_BE_NULL);
            return false;
        }
        if (source.getId() != null && !targetId.equals(source.getId())) {
            loggingExceptionHandler(Ingredient.INVALID_UPDATE_SOURCE_ID);
            return false;
        }
        if (!isValidIngredient(source)) {
            loggingExceptionHandler(INGREDIENT_UPDATE_SOURCE_FAILED_VALIDATION);
            return false;
        }
        if (source.getSteps() == null) {
            loggingExceptionHandler(Ingredient.INGREDIENT_STEPS_CANNOT_BE_NULL);
            return false;
        }
        return true;
    }

    /* Logging methods */
    @Transactional
    public Ingredient nullIngredientLogHandler (String logMessage) {
        logger.info(logMessage);
        return null;
    }

    @Transactional
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}
