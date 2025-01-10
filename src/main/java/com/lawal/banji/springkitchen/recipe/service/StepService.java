package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.repo.StepRepo;
import com.lawal.banji.springkitchen.recipe.model.Step;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StepService {

    public static final String METHOD_DOES_NOT_ALLOW_NULL_STEP_AS_PARAMETER = "Null step cannot be passed to StepService method";
    public static final String STEP_ID_CANNOT_BE_NULL = "StepService method does not accept null stepID";
    public static final String STEP_FAILED_VALIDATION = "The step did not pass StepService validation checks";
    public static final String STEP_NOT_FOUND_BY_ID = "StepService did not find item with ID %d";
    public static final String STEP_REPO_IS_EMPTY = "StepRepo is empty";
    public static final String SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE = "ONe or more validation checks failed.StepService.update() failed";
    public static final String STEP_SEARCH_QUERY_CANNOT_BE_EMPTY = "Search query cannot be null or empty";
    public static final String STEP_UPDATE_SOURCE_FAILED_VALIDATION = "Step update source validation failed";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(StepService.class);

    private final StepRepo stepRepo;

    @Autowired
    public StepService(StepRepo stepRepo) {
        this.stepRepo = stepRepo;
    }


    /* Create methods */
    @Transactional
    public Step save (Step step) {
        if (!isValidStep(step)) loggingExceptionHandler(STEP_FAILED_VALIDATION);
        return stepRepo.save(step);
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() { return stepRepo.count();}

    @Transactional(readOnly = true)
    public Step findById(Long id) {
        if (id == null) loggingExceptionHandler(STEP_ID_CANNOT_BE_NULL);
        else {
            Step step = stepRepo.findById(id).orElse(null);
            if (step == null) return nullStepLogHandler(String.format(STEP_NOT_FOUND_BY_ID, id));
            return stepRepo.save(step);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Step> findAll() { return stepRepo.findAll(); }

    @Transactional(readOnly = true)
    public Set<Step> filterByRecipe(Recipe recipe) {
        return stepRepo.findAll().stream()
                .filter(step -> step.getRecipe().equals(recipe))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<Step> filterByIngredient(Ingredient ingredient) {
        return stepRepo.findAll().stream()
                .filter(step -> step.getIngredient().equals(ingredient))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<Step> search(String string) {
        if (string == null || string.trim().isBlank()) {
            return Collections.emptySet();
        }
        return new HashSet<>(stepRepo.searchByDirectionsContainingIgnoreCase(string));
    }

    @Transactional(readOnly = true)
    public Step randomStep() {
        long count = count();
        if (count == 0) {
            logger.info(STEP_REPO_IS_EMPTY + " StepService.randomIngredient() cannot return random step");
            return null;
        }
        return stepRepo.findAll().get(random.nextInt((int) count));
    }

    @Transactional(readOnly = true)
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (Step step : stepRepo.findAll()) {
            stringBuilder.append(step.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /* Update methods */
    @Transactional
    public Step update (Long targetId, Step source) {
        if (!areValidUpdateParameters(targetId, source)) loggingExceptionHandler(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);
        Step target = findById(targetId);
        if (target == null) return nullStepLogHandler(STEP_NOT_FOUND_BY_ID + " StepService.update() failed");
        target.getUpdate(source);
        return stepRepo.save(target);
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        if (id == null) { loggingExceptionHandler(STEP_ID_CANNOT_BE_NULL); }
        else stepRepo.deleteById(id);
    }

    /* Validation methods */
    @Transactional
    public boolean isValidStep(Step step) {
        if (step == null) {
            logger.error(METHOD_DOES_NOT_ALLOW_NULL_STEP_AS_PARAMETER);
            return false;
        }
        if (step.getDirections() == null || step.getDirections().isEmpty()) {
            logger.error(Step.STEP_DIRECTIONS_CANNOT_BE_NULL_OR_BLANK);
            return false;
        }
        if (step.getIngredientAmount() < 0) {
            logger.error(Step.STEP_INGREDIENT_AMOUNT_CANNOT_BE_NEGATIVE);
            return false;
        }
        return true;
    }

    @Transactional
    public boolean areValidUpdateParameters(Long targetId, Step source) {
        if (targetId == null || source.getId() == null) {
            loggingExceptionHandler(STEP_ID_CANNOT_BE_NULL);
            return false;
        }
        if (source.getId() != null && !targetId.equals(source.getId())) {
            loggingExceptionHandler(Step.INVALID_UPDATE_SOURCE_ID);
            return false;
        }
        if (!isValidStep(source)) {
            loggingExceptionHandler(STEP_UPDATE_SOURCE_FAILED_VALIDATION);
            return false;
        }
        return true;
    }

    /* Logging methods */
    @Transactional
    public Step nullStepLogHandler (String logMessage) {
        logger.info(logMessage);
        return null;
    }

    @Transactional
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}