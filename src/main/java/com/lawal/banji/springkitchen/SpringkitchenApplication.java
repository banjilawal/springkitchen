package com.lawal.banji.springkitchen;


import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.pantry.PantryItem;
import com.lawal.banji.springkitchen.pantry.PantryItemService;
import com.lawal.banji.springkitchen.dataset.RecipeDescriptionDataset;
import com.lawal.banji.springkitchen.dataset.RecipeTitleDataset;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.step.service.StepService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class SpringkitchenApplication {

    public static final int NUMBER_OF_PANTRY_ITEMS = 15;
    public static final int NUMBER_OF_INGREDIENTS = 70;
    public static final int NUMBER_OF_RECIPE_STEPS = 100;

    public static final int NUMBER_OF_RECIPES = 10;
    public static final int MIN_STEPS_PER_RECIPE = 3;
    public static final int MAX_STEPS_PER_RECIPE = 11;

    public static final Long MIN_REORDER_LEVEL = 10L;
    public static final Long MAX_REORDER_LEVEL = 25L;


    public static void main(String[] args) {
        SpringApplication.run(SpringkitchenApplication.class, args);
    }
}