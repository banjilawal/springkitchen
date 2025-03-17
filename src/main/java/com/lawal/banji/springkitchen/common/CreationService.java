package com.lawal.banji.springkitchen.common;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreationService {

    private final int MAX_INGREDIENT_OCCURRENCES = 3;


    public static final LocalDateTime DATE_TIME_FLOOR = LocalDateTime.now().minusWeeks(6);
    public static final LocalDateTime DATE_TIME_CEILING = LocalDateTime.now().plusWeeks(6);

    private final RandomPropertyService randomPropertyService;

    @Autowired
    public CreationService(RandomPropertyService randomPropertyService) {
        this.randomPropertyService = randomPropertyService;
    }

    public Food createFood() {
        return new Food(null, randomPropertyService.foodName());
    }

    public Recipe createRecipe() {
        return new Recipe(null, randomPropertyService.recipeTitle(), randomPropertyService.recipeDescription());
    }

    public Step createStep(Food food, Recipe recipe) {
        return new Step(null, recipe, food, randomPropertyService.stepInstruction());
    }

    public Meal createMeal(Recipe recipe) {
        return new Meal(null, randomPropertyService.timestamp(DATE_TIME_FLOOR, DATE_TIME_CEILING), recipe);
    }
}