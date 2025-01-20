package com.lawal.banji.springkitchen.food.service;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.FoodRepo;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceDeleteOperationFailed;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceRepoEmptyOrNull;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceSaveOperationFailed;
import com.lawal.banji.springkitchen.food.service.exception.FoodServiceUpdateExceptionTargetNotFound;
import com.lawal.banji.springkitchen.global.AppLogger;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodService {

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepo foodRepo;

    @Autowired
    public FoodService(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    /* Create methods */
    @Transactional
    public Food save (Food food) {
       FoodServiceValidator.validateSaveFoodParameter(food);
       AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.SAVING_FOOD_MESSAGE);
       try {
           Food savedFood = foodRepo.save(food);
           AppLogger.info(FoodService.class, FoodServiceLoggingMessage.SUCCESSFULLY_SAVE_FOOD + savedFood.toString());
           return savedFood;
        } catch (DataAccessException e) {
           AppLogger.error(FoodService.class, String.format(FoodServiceSaveOperationFailed.MESSAGE + e.getMessage()), e);
           throw e;
        }
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() {
        Long totalFoods = foodRepo.count();
        AppLogger.info(FoodService.class, totalFoods + FoodServiceLoggingMessage.NUMBER_OF_FOODS_MESSAGE);
        return totalFoods;
    }

    @Transactional(readOnly = true)
    public Food findById(Long id) {
        FoodServiceValidator.validateFoodServiceMethodLongParameter(id);
        AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.FINDING_FOOD_BY_ID_MESSAGE + id);
        Food food = foodRepo.findById(id).orElse(null);
        if (food == null) {
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.FOOD_NOT_FOUND_BY_ID_MESSAGE + id);
        } else {
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.FOUND_FOOD_BY_ID_MESSAGE + food.toString());
        }
        return food;
    }

    @Transactional(readOnly = true)
    public Food findByName(String name) {
        FoodServiceValidator.validateFoodServiceMethodStringParameter(name);
        AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.FINDING_FOOD_BY_NAME_MESSAGE + name);
        Food food = foodRepo.findByNameIgnoreCase(name);
        if (food == null) {
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.FOOD_NOT_FOUND_BY_NAME_MESSAGE + name);
        } else {
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.FOUND_FOOD_BY_NAME_MESSAGE + food.toString());
        }
        return food;
    }

    @Transactional(readOnly = true)
    public List<Food> findAll() {
        AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.FETCHING_ALL_FOOD_MESSAGE);
        List<Food> foods = foodRepo.findAll();
        AppLogger.info(FoodService.class, FoodServiceLoggingMessage.FOUND_ALL_FOOD_MESSAGE + foods.size());
        return List.copyOf(foods);
    }

    @Transactional(readOnly = true)
    public Set<Food> search(String string) {
        Set<Food> matches = new HashSet<>();
        AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.SEARCHING_FOR_MATCHES_BY_STRING_MESSAGE + string);
        if (string != null && !string.isBlank()) {
            for (Food food : foodRepo.findAll()) {
                if (food.getName().toLowerCase().contains(string.toLowerCase())) matches.add(food);
            }
        }
        AppLogger.info(FoodService.class, matches.size() + FoodServiceLoggingMessage.TOTAL_MATCHES_TO_STRING_FOUND_MESSAGE + string);
        return matches;
    }

    @Transactional(readOnly = true)
    public Food randomFood() {
        AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.RANDOMLY_SELECTING_FOOD_MESSAGE);
        int count = (int) foodRepo.count();
        if (count == 0) {
            AppLogger.error(FoodService.class, FoodServiceRepoEmptyOrNull.MESSAGE, new FoodServiceRepoEmptyOrNull());
            throw new FoodServiceRepoEmptyOrNull();
        }
        Food food = foodRepo.findAll().get(random.nextInt(0, count));
        if (food == null) {
            AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.FAILED_TO_GET_RANDOM_FOOD_FROM_NONEMPTY_REPO_MESSAGE);
        } else {
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.RANDOMLY_SELECTING_FOOD_MESSAGE + food.toString());
        }
        return food;
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
        FoodServiceValidator.validateUpdateFoodParameters( targetId, source);
        try {
            Food target = findById(targetId);
            if (target == null) {
                AppLogger.error(FoodService.class,
                    FoodServiceLoggingMessage.FOOD_NOT_FOUND_BY_ID_MESSAGE + targetId,
                    new FoodServiceUpdateExceptionTargetNotFound()
                );
                throw new FoodServiceUpdateExceptionTargetNotFound();
            }
            target.getUpdate(source);
            Food savedFood = foodRepo.save(target);
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.SUCCESSFULLY_UPDATED_FOOD_MESSAGE + savedFood.toString());
            return savedFood;
        } catch (DataAccessException e) {
            AppLogger.error(FoodService.class, FoodServiceSaveOperationFailed.MESSAGE + e.getMessage(), e);
            throw e;
        }
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        FoodServiceValidator.validateFoodServiceMethodLongParameter(id);
        AppLogger.debug(FoodService.class, FoodServiceLoggingMessage.DELETING_FOOD_MESSAGE);
        try {
            foodRepo.deleteById(id);
            AppLogger.info(FoodService.class, FoodServiceLoggingMessage.SUCCESSFULLY_DELETE_FOOD);
        } catch (DataAccessException e) {
            AppLogger.error(FoodService.class, FoodServiceDeleteOperationFailed.MESSAGE + e.getMessage(), e);
            throw e;
        }
    }
}
