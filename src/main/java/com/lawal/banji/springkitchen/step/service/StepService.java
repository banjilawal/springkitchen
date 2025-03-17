package com.lawal.banji.springkitchen.step.service;


import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.common.AppLogger;
import com.lawal.banji.springkitchen.recipe.model.Recipe;

import com.lawal.banji.springkitchen.step.StepRepo;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceDeleteOperationFailed;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceUpdateExceptionTargetNotFound;
import com.lawal.banji.springkitchen.step.service.exception.StepServiceUpdateOperationFailed;
import org.springframework.dao.DataAccessException;
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
        StepServiceValidator.validateSaveStepParameter( step);
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.SAVING_STEP_MESSAGE);
        try {
            Step savedStep = stepRepo.save(step);
            AppLogger.info(StepService.class, StepServiceLoggingMessage.SUCCESSFULLY_SAVE_STEP + savedStep.toString());
            return savedStep;
        } catch (DataAccessException e) {
            AppLogger.error(StepService.class, String.format(StepServiceLoggingMessage.SAVING_STEP_FAILED + e.getMessage()), e);
            throw e;
        }
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() {
        Long totalSteps = stepRepo.count();
        AppLogger.debug(StepService.class, totalSteps + StepServiceLoggingMessage.NUMBER_OF_STEPS_MESSAGE);
        return totalSteps;
    }

    @Transactional(readOnly = true)
    public Step findById(Long id) {
        StepServiceValidator.validateLongMethodParameter(id);
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.FINDING_STEP_BY_ID_MESSAGE + id);
        Step step = stepRepo.findById(id).orElse(null);
        if (step == null) {
            AppLogger.info(StepService.class, StepServiceLoggingMessage.STEP_NOT_FOUND_BY_ID_MESSAGE + id);
        } else {
            AppLogger.info(StepService.class, StepServiceLoggingMessage.FOUND_STEP_BY_ID_MESSAGE + step.toString());
        }
        return step;
    }

    @Transactional(readOnly = true)
    public List<Step> findAll() {
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.FETCHING_ALL_STEPS_MESSAGE);
        List<Step> steps = stepRepo.findAll();
        AppLogger.info(StepService.class, StepServiceLoggingMessage.FOUND_ALL_STEPS_MESSAGE + steps.size());
        return List.copyOf(steps);
    }

    @Transactional(readOnly = true)
    public Set<Step> filterByRecipe(Recipe recipe) {
        StepServiceValidator.validateRecipeMethodParameter(recipe);
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.SELECTED_STEPS_BY_RECIPE_MESSAGE);
        try {
            Set<Step> matches = stepRepo.findAll().stream()
                .filter(step -> step.getRecipe().equals(recipe))
                .collect(Collectors.toSet());
            AppLogger.info(StepService.class, matches.size() + StepServiceLoggingMessage.TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE + recipe.toString());
            return matches;
        } catch (DataAccessException e) {
            AppLogger.error(StepService.class, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Set<Step> filterByIngredient(Food ingredient) {
        StepServiceValidator.validateIngredientMethodParameter(ingredient);
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.SELECTED_STEPS_BY_INGREDIENT_MESSAGE);
        try {
            Set<Step> matches = stepRepo.findAll().stream()
                .filter(step -> step.getIngredient().equals(ingredient))
                .collect(Collectors.toSet());
            AppLogger.info(StepService.class, matches.size() + StepServiceLoggingMessage.TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE + ingredient.toString());
            return matches;
        } catch (DataAccessException e) {
            AppLogger.error(StepService.class, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Set<Step> search(String string) {
        Set<Step> matches = new HashSet<>();
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.SEARCHING_FOR_MATCHES_BY_STRING_MESSAGE + string);
        if (string != null && !string.isBlank()) {
            string = string.toLowerCase();
            for (Step step : stepRepo.findAll()) {
                if (step.getInstruction().toLowerCase().contains(string)) matches.add(step);
            }
        }
        AppLogger.info(StepService.class, matches.size() + StepServiceLoggingMessage.TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE + string);
        if (string == null || string.trim().isBlank()) {
            return Collections.emptySet();
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Step randomStep() {
        long count = count();
        Step step = stepRepo.findAll().get(random.nextInt((int) count));
        if (count == 0) {
            AppLogger.debug(StepService.class, StepServiceLoggingMessage.NO_RANDOM_STEP_AVAILABLE_MESSAGE);
        } else if (step == null) {
            AppLogger.debug(StepService.class, StepServiceLoggingMessage.FAILED_TO_GET_RANDOM_STEP_FROM_NONEMPTY_REPO_MESSAGE);
        } else {
            AppLogger.info(StepService.class, StepServiceLoggingMessage.RANDOMLY_SELECTING_STEP_MESSAGE + step.toString());
        }
        return step;
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
        StepServiceValidator.validateUpdateStepParameters(targetId, source);
        try {
            Step target = findById(targetId);
            if (target == null) {
                AppLogger.error(StepService.class, StepServiceUpdateExceptionTargetNotFound.MESSAGE + " "  + targetId, new StepServiceUpdateExceptionTargetNotFound());
                throw new StepServiceUpdateExceptionTargetNotFound();
            }
            target.getUpdate(source);
            Step savedStep = stepRepo.save(target);
            AppLogger.info(StepService.class, StepServiceLoggingMessage.SUCCESSFULLY_UPDATED_STEP_MESSAGE + savedStep.toString());
            return savedStep;
        } catch (DataAccessException e) {
            AppLogger.error(StepService.class, StepServiceUpdateOperationFailed.MESSAGE + e.getMessage(), e);
            throw e;
        }
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        StepServiceValidator.validateLongMethodParameter(id);
        AppLogger.debug(StepService.class, StepServiceLoggingMessage.DELETING_STEP_MESSAGE);
        try {
            Step step = findById(id);
            if (step == null) {
                System.out.println("No step with id to delete");
                return;
            }
            Recipe recipe = step.getRecipe();
            Food ingredient = step.getIngredient();

            if (recipe != null) recipe.removeStep(step);
            if (ingredient != null) ingredient.removeStep(step);


            stepRepo.deleteById(id);
            AppLogger.info(StepService.class, StepServiceLoggingMessage.SUCCESSFULLY_DELETED_STEP);
        } catch (DataAccessException e) {
            AppLogger.error(StepService.class, StepServiceDeleteOperationFailed.MESSAGE + " " + e.getMessage(), e);
            throw e;
        }
    }

    /* Validation methods */
    @Transactional
    public boolean isValidStep(Step step) {
        if (step == null) {
            logger.error(METHOD_DOES_NOT_ALLOW_NULL_STEP_AS_PARAMETER);
            return false;
        }
        if (step.getInstruction() == null || step.getInstruction().isEmpty()) {
            logger.error(Step.STEP_DIRECTIONS_CANNOT_BE_NULL_OR_BLANK);
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

    @Transactional
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}