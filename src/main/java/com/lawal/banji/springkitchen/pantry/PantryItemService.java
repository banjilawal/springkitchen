package com.lawal.banji.springkitchen.pantry;

import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PantryItemService {

    public static final String PANTRY_ITEM_REPO_IS_EMPTY = "PantryItemRepo is empty";

    public static final String PANTRY_ITEM_NOT_FOUND_BY_ID = "PantryItemService did not find item with ID %d";
    public static final String PANTRY_ITEM_NOT_FOUND_BY_NAME = "PantryItemService did not find item with NAME %s";

    public static final String PANTRY_ITEM_ID_CANNOT_BE_NULL = "PantryItemService method does not accept null pantryItemID";
    public static final String PANTRY_ITEM_FAILED_VALIDATION = "The pantry item did not pass PantryItemService validation checks";
    public static final String SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE = "One or more validation checks failed. PantryItemService.update() failed";
    public static final String METHOD_DOES_NOT_ALLOW_NULL_PANTRY_ITEM_AS_PARAMETER = "Null pantryItem cannot be passed to PantryItemService method";

    public static final String PANTRY_ITEM_UPDATE_SOURCE_FAILED_VALIDATION = "PantryItem update source validation failed";

    public static final String PANTRY_ITEM_SEARCH_QUERY_CANNOT_BE_EMPTY = "Search query cannot be null or empty";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(PantryItemService.class);

    private final PantryItemRepo pantryItemRepo;

    @Autowired
    public PantryItemService(PantryItemRepo pantryItemRepo) {
        this.pantryItemRepo = pantryItemRepo;
    }

    /* Create methods */
    @Transactional
    public PantryItem save (PantryItem pantryItem) {
        if (!isValidPantryItem(pantryItem)) loggingExceptionHandler(PANTRY_ITEM_FAILED_VALIDATION);
        return pantryItemRepo.save(pantryItem);
    }

    /* Read methods */
    @Transactional(readOnly = true)
    public Long count() {
        return pantryItemRepo.count();
    }

    @Transactional(readOnly = true)
    public PantryItem findById(Long id) {
        if (id == null) loggingExceptionHandler(PANTRY_ITEM_ID_CANNOT_BE_NULL);
        else {
            PantryItem pantryItem = pantryItemRepo.findById(id).orElse(null);
            if (pantryItem == null) return nullPantryItemLogHandler(String.format(PANTRY_ITEM_NOT_FOUND_BY_ID, id));
            return pantryItemRepo.save(pantryItem);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public PantryItem findByName(String name) {
        if (name == null || name.isBlank()) loggingExceptionHandler(PantryItem.PANTRY_ITEM_NAME_CANNOT_BE_NULL_OR_BLANK);

        PantryItem pantryItem = pantryItemRepo.findByNameIgnoreCase(name);
        if (pantryItem == null) { return nullPantryItemLogHandler(String.format(PANTRY_ITEM_NOT_FOUND_BY_NAME, name)); }
        else {
            return pantryItemRepo.save(pantryItem);
        }
    }

    @Transactional(readOnly = true)
    public List<PantryItem> findAll() {
        return pantryItemRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Set<PantryItem> search(String string) {
        if (string == null || string.trim().isBlank()) {
            return Collections.emptySet();
        }
        return new HashSet<>(pantryItemRepo.findByNameContainingIgnoreCase(string));
    }

    @Transactional(readOnly = true)
    public PantryItem randomPantryItem() {
        long count = count();
        if (count == 0) {
            logger.info(PANTRY_ITEM_REPO_IS_EMPTY + " PantryItemService.randomRecipe() cannot return random pantryItem");
            return null;
        }
        return pantryItemRepo.findAll().get(random.nextInt((int) count));
    }

    @Transactional(readOnly = true)
    public String toString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (PantryItem PantryItem : pantryItemRepo.findAll()) {
            stringBuilder.append(PantryItem.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /* Update methods */
    @Transactional
    public PantryItem update (Long targetId, PantryItem source) {
        if (!areValidUpdateParameters(targetId, source)) loggingExceptionHandler(SERVICE_DOES_NOT_AUTHORIZE_ITEM_UPDATE);

        PantryItem target = findById(targetId);
        if (target == null) return nullPantryItemLogHandler(String.format(PANTRY_ITEM_NOT_FOUND_BY_ID, targetId));

        target.getUpdate(source);
        return pantryItemRepo.save(target);
    }

    /* Delete methods */
    @Transactional
    public void deleteById(Long id) {
        if (id == null) { loggingExceptionHandler(PANTRY_ITEM_ID_CANNOT_BE_NULL); }
        else pantryItemRepo.deleteById(id);
    }

    /* Validation methods */
    @Transactional
    public boolean isValidPantryItem(PantryItem pantryItem) {
        if (pantryItem == null) {
            loggingExceptionHandler(METHOD_DOES_NOT_ALLOW_NULL_PANTRY_ITEM_AS_PARAMETER);
            return false;
        }
        if (pantryItem.getName() == null || pantryItem.getName().isBlank()) {
            loggingExceptionHandler(PantryItem.PANTRY_ITEM_NAME_CANNOT_BE_NULL_OR_BLANK);
            return false;
        }
        if (pantryItem.getQuantityInStock() == null || pantryItem.getQuantityInStock() < 0) {
            loggingExceptionHandler(PantryItem.QUANTITY_IN_STOCK_MUST_NOT_BE_NULL_OR_LESS_THAN_ZERO);
            return false;
        }
        if (pantryItem.getReorderLevel() == null || pantryItem.getReorderLevel() < 0) {
            loggingExceptionHandler(PantryItem.REORDER_LEVEL_MUST_NOT_BE_NULL_OR_LESS_THAN_ZERO);
            return false;
        }
        return false;
    }

    @Transactional
    public boolean areValidUpdateParameters(Long targetId, PantryItem source) {
        if (targetId == null || source.getId() == null) {
            loggingExceptionHandler(PANTRY_ITEM_ID_CANNOT_BE_NULL);
            return false;
        }
        if (source.getId() != null && !targetId.equals(source.getId())) {
            loggingExceptionHandler(PantryItem.INVALID_UPDATE_SOURCE_ID);
            return false;
        }
        if (!isValidPantryItem(source)) {
            loggingExceptionHandler(PANTRY_ITEM_UPDATE_SOURCE_FAILED_VALIDATION);
            return false;
        }
        return true;
    }

    /* Logging methods */
    @Transactional
    public PantryItem nullPantryItemLogHandler (String logMessage) {
        logger.info(logMessage);
        return null;
    }

    @Transactional
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}
