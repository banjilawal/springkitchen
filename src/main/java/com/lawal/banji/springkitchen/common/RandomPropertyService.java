package com.lawal.banji.springkitchen.common;

import com.lawal.banji.springkitchen.dataset.FoodNameDataset;
import com.lawal.banji.springkitchen.dataset.RecipeDescriptionDataset;
import com.lawal.banji.springkitchen.dataset.RecipeTitleDataset;
import com.lawal.banji.springkitchen.dataset.StepStatementDataset;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.step.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RandomPropertyService {

    private final FoodService foodService;
    private final RecipeService recipeService;
    private final StepService stepService;

    @Autowired
    public RandomPropertyService (FoodService foodService, RecipeService recipeService, StepService stepService) {
        this.foodService = foodService;
        this.recipeService = recipeService;
        this.stepService = stepService;
    }

    public String recipeTitle () {
        String title = RecipeTitleDataset.title();
        while (recipeService.findByTitle(title) != null) {
            title = RecipeTitleDataset.title();
        }
        return title;
    }

    public String recipeDescription() {
        String description = RecipeDescriptionDataset.description();
        while (recipeService.findByDescription(description) != null) {
            description = RecipeDescriptionDataset.description();
        }
        return description;
    }

    public String foodName() {
        String name = FoodNameDataset.name();
        while (foodService.findByName(name) != null) {
            name = FoodNameDataset.name();
        }
        return name;
    }

    public String stepInstruction() {
        return StepStatementDataset.instruction();
    }

    public LocalDateTime timestamp (LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        // Convert LocalDateTime to epoch seconds for randomization
        long startSeconds = startDate.toEpochSecond(ZoneOffset.UTC);
        long endSeconds = endDate.toEpochSecond(ZoneOffset.UTC);

        // Generate a random epoch second within the range
        long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds + 1);

        // Convert epoch seconds back to LocalDateTime
        return LocalDateTime.ofEpochSecond(randomSeconds, 0, ZoneOffset.UTC);
    }
}