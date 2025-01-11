package com.lawal.banji.springkitchen.search;

import com.lawal.banji.springkitchen.food.Food;
import com.lawal.banji.springkitchen.pantry.PantryItem;
import com.lawal.banji.springkitchen.pantry.PantryItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PantryIngredientSearchService {

    private static final Logger logger = LoggerFactory.getLogger(PantryIngredientSearchService.class);

    private final PantryItemService pantryItemService;

    @Autowired
    public PantryIngredientSearchService(PantryItemService pantryItemService) {
        this.pantryItemService = pantryItemService;
    }

    /* Search methods */
    @Transactional(readOnly = true)
    Set<PantryItem> searchForIngredient (Food food) {
        if (food == null || food.getName() == null || food.getName().trim().isBlank()) {
            return Collections.emptySet();
        } else { return pantryItemService.search(food.getName()); }
    }

    /* Create methods */

    /* Read methods */

    /* Update methods */

    /* Delete methods */

    /* Validation methods */
    /* Logging methods */
}
