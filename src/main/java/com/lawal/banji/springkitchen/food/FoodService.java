package com.lawal.banji.springkitchen.food;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodService {

    public static final String INGREDIENT_REPO_IS_EMPTY = "FoodRepo is empty";

    public static final String INGREDIENT_NOT_FOUND_BY_ID = "FoodService did not find item with ID %d";
    public static final String INGREDIENT_NOT_FOUND_BY_NAME = "FoodService did not find item with NAME %s";

    public static final String INGREDIENT_ID_CANNOT_BE_NULL = "FoodService method does not accept null ingredientID";
    public static final String INGREDIENT_FAILED_VALIDATION = "The ingredient did not pass FoodService validation checks";

    public static final String INGREDIENT_UPDATE_SOURCE_FAILED_VALIDATION = "Food update source validation failed";
    public static final String SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE = "ONe or more validation checks failed. FoodService.update() failed";
    public static final String METHOD_DOES_NOT_ALLOW_NULL_INGREDIENT_AS_PARAMETER = "Null ingredient cannot be passed to FoodService method";

    public static final String FAILED_TO_SAVE_INGREDIENT = "FoodService.save() failed error: %s";

    public static final String INGREDIENT_SEARCH_QUERY_CANNOT_BE_EMPTY = "Search query cannot be null or empty";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepo foodRepo;
    private final FoodValidator foodValidator;

    @Autowired
    public FoodService(FoodRepo foodRepo, FoodValidator foodValidator) {
        this.foodRepo = foodRepo;
        this.foodValidator = foodValidator;
    }

    /* Create methods */
    @Transactional
    public Food save (Food food) {
        if (!isValidIngredient(food)) { illegalArgumentExceptionLogger(INGREDIENT_FAILED_VALIDATION); }
        try {
            return foodRepo.save(food);
        } catch (DataAccessException e) {
            runtimeExceptionLogger(String.format(FAILED_TO_SAVE_INGREDIENT, e.getMessage()));
            throw e;
        }
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() { return foodRepo.count();}

    @Transactional(readOnly = true)
    public Food findById(Long id) {
        try {
            if (id == null) {
                illegalArgumentExceptionLogger(INGREDIENT_ID_CANNOT_BE_NULL);
                throw new IllegalArgumentException(INGREDIENT_ID_CANNOT_BE_NULL);
            }
            Food food = foodRepo.findById(id).orElse(null);
            if (food == null) {
                return nullIngredientLogHandler(String.format(INGREDIENT_NOT_FOUND_BY_ID, id));
            }
            return foodRepo.save(food);
        } catch (DataAccessException e) {
           runtimeExceptionLogger((INGREDIENT_NOT_FOUND_BY_ID + " " + e.getMessage()));
           throw e;
        }
    }

    @Transactional(readOnly = true)
    public Food findByName(String name) {
        try {
            if (name == null || name.isBlank()) {
                illegalArgumentExceptionLogger(Food.INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);
                throw new IllegalArgumentException(Food.INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);
            }
            Food food = foodRepo.findByNameIgnoreCase(name);
            if (food == null) { return nullIngredientLogHandler(String.format(INGREDIENT_NOT_FOUND_BY_NAME, name)); }
            if (food.getSteps() == null) food.setSteps(new HashSet<>());
            return foodRepo.save(food);
        } catch (DataAccessException e) {
            runtimeExceptionLogger((INGREDIENT_NOT_FOUND_BY_NAME + " " + e.getMessage()));
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<Food> findAll() {
        try {
            return foodRepo.findAll();
        } catch (DataAccessException e) {
            runtimeExceptionLogger(e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Set<Food> search(String string) {
        try {
            if (string == null || string.trim().isBlank()) {
                return Collections.emptySet();
            }
            return new HashSet<>(foodRepo.findByNameContainingIgnoreCase(string));
        } catch (DataAccessException e) {
            runtimeExceptionLogger(e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Food randomIngredient() {
        long count = count();
        if (count == 0) {
            logger.info(INGREDIENT_REPO_IS_EMPTY + " FoodService.randomIngredient() cannot return random ingredient");
            return null;
        }
        return foodRepo.findAll().get(random.nextInt((int) count));
    }

    @Transactional(readOnly = true)
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (Food food : foodRepo.findAll()) {
            stringBuilder.append(food.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /* Update methods */
    @Transactional
    public Food update (Long targetId, Food source) {
        try {
            if (!areValidUpdateParameters(targetId, source)) {
                illegalArgumentExceptionLogger(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);
                throw new IllegalArgumentException(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);
            }
            Food target = findById(targetId);
            if (target == null) { return nullIngredientLogHandler(String.format(INGREDIENT_NOT_FOUND_BY_ID, targetId)); }
            target.getUpdate(source);
            return foodRepo.save(target);
        } catch (DataAccessException e) {
            runtimeExceptionLogger(e.getMessage());
            throw e;
        }
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        try {
            if (id == null) {
                illegalArgumentExceptionLogger(INGREDIENT_ID_CANNOT_BE_NULL);
                throw new IllegalArgumentException(INGREDIENT_ID_CANNOT_BE_NULL);
            }
            else foodRepo.deleteById(id);
        } catch (DataAccessException e) {
            runtimeExceptionLogger(e.getMessage());
            throw e;
        }
    }

    /* Validation methods */
    @Transactional
    public boolean isValidIngredient(Food food) {
        if (food == null) {
            illegalArgumentExceptionLogger(METHOD_DOES_NOT_ALLOW_NULL_INGREDIENT_AS_PARAMETER);
            return false;
        }
        if (food.getName() == null || food.getName().trim().isBlank()) {
            illegalArgumentExceptionLogger(Food.INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);
            return false;
        }
        if (food.getSteps() == null) food.setSteps(new HashSet<>());
        return true;
    }

    @Transactional
    public boolean areValidUpdateParameters(Long targetId, Food source) {
        if (targetId == null || source.getId() == null) {
            illegalArgumentExceptionLogger(INGREDIENT_ID_CANNOT_BE_NULL);
            return false;
        }
        if (source.getId() != null && !targetId.equals(source.getId())) {
            illegalArgumentExceptionLogger(Food.INVALID_UPDATE_SOURCE_ID);
            return false;
        }
        if (!isValidIngredient(source)) {
            illegalArgumentExceptionLogger(INGREDIENT_UPDATE_SOURCE_FAILED_VALIDATION);
            return false;
        }
        if (source.getSteps() == null) {
            illegalArgumentExceptionLogger(Food.INGREDIENT_STEPS_CANNOT_BE_NULL);
            return false;
        }
        return true;
    }

    /* Logging methods */
    @Transactional
    public Food nullIngredientLogHandler (String logMessage) {
        logger.info(logMessage);
        return null;
    }

    @Transactional
    public void illegalArgumentExceptionLogger(String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }

    @Transactional
    public void runtimeExceptionLogger (String errorMessage) { logger.error(errorMessage); }
}
