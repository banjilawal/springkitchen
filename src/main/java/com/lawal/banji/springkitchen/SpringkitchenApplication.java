package com.lawal.banji.springkitchen;


import com.lawal.banji.springkitchen.pantry.PantryItem;
import com.lawal.banji.springkitchen.pantry.PantryItemService;
import com.lawal.banji.springkitchen.dataset.RecipeDescriptionDataset;
import com.lawal.banji.springkitchen.dataset.RecipeTitleDataset;
import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.IngredientService;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.recipe.service.StepService;
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

    @Bean
    @Transactional // Ensuring transactional context for all operations
    public CommandLineRunner seedData(
        PantryItemService pantryItemService,
        IngredientService ingredientService,
        StepService stepService,
        RecipeService recipeService
    ) {
        return args -> {

            System.out.println("Seeding Pantry...");
            Set<PantryItem> pantryItems = FoodGenerator.foodSet(NUMBER_OF_PANTRY_ITEMS, MIN_REORDER_LEVEL, MAX_REORDER_LEVEL);
            pantryItems.forEach(pantryItemService::save);
            System.out.println("Pantry Seeded: " + pantryItemService.count());

            System.out.println("Seeding Recipes...");
            for (int i = 0; i < NUMBER_OF_RECIPES; i++) {
                String title = RecipeTitleDataset.recipeTitle(new HashSet<>(recipeService.findAll()));
                String description = RecipeDescriptionDataset.recipeDescription(new HashSet<>(recipeService.findAll()));
                Recipe recipe = new Recipe(null, title, description);
                System.out.println("Created " + recipe.toString());
                recipeService.save(recipe);
                System.out.println(recipeService.count() + " recipes created");
            }
            System.out.println(recipeService.toString());
            System.out.println("Recipes Seeded: " + recipeService.count());

            System.out.println("Random recipe:" + recipeService.randomRecipe());

            // 1. Seed Ingredients
            System.out.println("Seeding Ingredients...");
            Set<Ingredient> ingredients = IngredientGenerator.ingredients(40); // Generate 40 ingredients
            ingredients.forEach(ingredientService::save); // Save all ingredients to the database
            System.out.println("Ingredients Seeded: " + ingredients.size());
            System.out.println("Random ingredient:" + ingredientService.randomIngredient());
//
//            // Print all saved ingredients
//            int count = 0;
//            for (Ingredient ingredient : ingredientService.findAll()) {
//                System.out.println(count + " " + ingredient.toString());
//                count++;
//            }
//
//            // Print random ingredients
//            for (int i = 0; i < 5; i++) {
//                System.out.println("Random Ingredient: " + ingredientService.randomIngredient());
//            }
//
//            // Fetch saved Ingredients with IDs
//            List<Ingredient> savedIngredients = ingredientService.findAll();
//            if (savedIngredients.isEmpty()) {
//                throw new IllegalStateException(
//                        "No ingredients were saved to the database. Cannot proceed with Step generation.");
//            }

            // 2. Seed Steps using the saved Ingredients
//            for (Recipe recipe : recipeService.findAll()) {
//
//                for (int i = 0; i < new Random().nextInt(2, 12); i++) {
//                    Ingredient ingredient = ingredientService.randomIngredient();
//                    while (recipe.filterByIngredients(ingredient) != null) {
//                        ingredient = ingredientService.randomIngredient();
//                    }
//                    Step step = StepGenerator.step(recipe, ingredient);
//
//                    recipe.addStep(step);
//                    recipeService.save(recipe);
//                    ingredientService.save(ingredient);
//                    stepService.save(step);
//                }
//
//            }
//            System.out.println("Seeding Steps...");
//            int stepCount = 20; // Number of Steps to generate
//            List<Step> steps = stepService.generateSteps(savedIngredients, stepCount);
//            System.out.println("Steps Seeded: " + steps.size());
//
//            for (Step step : steps) {
//                System.out.println(step.toString());
//            }
//
//            // Validate ingredient-step relationships
//            System.out.println("Validating Ingredient-Step Relationships...");
//            for (Ingredient ingredient : ingredientService.findAll()) {
//                Set<Step> ingredientSteps = ingredient.getStepsAsSet(); // Fetch all steps for the ingredient
//                if (ingredientSteps.isEmpty()) {
//                    System.out.printf("Ingredient '%s' has no linked Steps.%n", ingredient.getName());
//                } else {
//                    System.out.printf("Ingredient '%s' is linked to Steps:%n", ingredient.getName());
//                    for (Step step : ingredientSteps) {
//                        System.out.printf("\t- Step '%s' with directions: '%s'%n",
//                                step.getId(), step.getDirections());
//                    }
//                }
//            }
//
//            for (Step step : stepService.findAll()) {
//                step.setIngredient(stepService.randomStep().getIngredient());
//                step.setIngredientAmount(new Random().nextDouble(1.0, 12.5));
//                step.setDirections(StepStatementDataset.statements());
//                stepService.save(step);
//                ingredientService.save(step.getIngredient());
//                System.out.println(step.toString());
//                System.out.println(step.getIngredient().toString());
//            }
//
//            System.out.println("Seeding Recipes...");
//            for (int i = 0; i < NUMBER_OF_RECIPES; i++) {
//
//                // Create and save a Recipe instance
//                Recipe recipe = new Recipe(null, MetadataGenerator.metadata());
//                recipeService.save(recipe); // Persist Recipe without any steps initially
//
//                // Randomly generate and persist Steps
//                stepCount = new Random().nextInt(MIN_STEPS_PER_RECIPE, MAX_STEPS_PER_RECIPE);
//                for (int j = 0; j < stepCount; j++) {
//                    Step step = stepService.random(); // Retrieve or generate a random step
//                    for (Step rs : recipe.getSteps()) {
//                        if (rs.getIngredient().getId().equals(step.getIngredient().getId()))
//                            step = stepService.random();
//                    }
//                    recipe.addStep(step);
//                    stepService.save(step); // Persist Step individually before relationship
//                }
//                recipeService.save(recipe);
//            }
//            System.out.println("Seeding Recipes Completed." + recipeService.countRecipes() + " recipes created.");
//            System.out.println("Data seeding completed successfully.");
//
//            for(Step step : stepService.findAll()){
//                System.out.println(step.toString());
//            }
        };
    }
//            // 2. Seed Steps
//            System.out.println("Seeding Steps...");
//                // Generate the specified number of steps
//                for (int i = 0; i < NUMBER_OF_RECIPE_STEPS;  i++) {
//                    Step step = new Step();
//                    step.setDirections(StepStatementDataset.statements());
//                    step.setDurationMinutes((long) (new Random().nextInt(60) + 1)); // Random duration between 1-60 minutes
//                    Ingredient ingredient = ingredientService.randomIngredient();
//                    if (ingredient == null) {
//                        throw new IllegalStateException("Random ingredient was null. Please ensure ingredients are correctly seeded.");
//                    }
//                    step.setIngredient(ingredient);
//                    step.setIngredientAmount(new Random().nextDouble(1, 5)); // Random amount between 1 and 5 units
//                    System.out.println("Step: " + step.toString());
//                    System.out.println("Ingredient: " + ingredient.toString());
//                    stepService.save(step);
//                    ingredientService.save(ingredient);
//                }
//            System.out.println("Seeding Recipes...");
//            Set<Recipe> recipes = RecipeGenerator.recipes(NUMBER_OF_RECIPES, MIN_STEPS_PER_RECIPE, MAX_STEPS_PER_RECIPE, stepService.findAll()); // Pass seeded steps
//            recipes.forEach(recipeService::save);
//            System.out.println("Recipes Seeded: " + recipes.size());
//        };
//    }
}