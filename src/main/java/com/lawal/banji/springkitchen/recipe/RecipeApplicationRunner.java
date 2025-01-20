package com.lawal.banji.springkitchen.recipe;


import com.lawal.banji.springkitchen.dataset.RecipeDescriptionDataset;
import com.lawal.banji.springkitchen.dataset.RecipeTitleDataset;
import com.lawal.banji.springkitchen.dataset.StepStatementDataset;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealOrchestratorService;
import com.lawal.banji.springkitchen.recipe.controller.RecipeRestController;

import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

@Component
@Order(2)
public class RecipeApplicationRunner implements CommandLineRunner {

    private static final Random random = new Random();

    @Autowired
    private RecipeMealOrchestratorService recipeMealOrchestratorService;

    @Autowired
    private RecipeRestController recipeRestController;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting RecipeApplicationRunner...");

        // Test FoodRestController
        testRecipeRESTController();
        System.out.println("Finished RecipeApplicationRunner...");

    }


    private void testRecipeRESTController() {
        System.out.println("Testing RecipeRestController...");

        for (int i = 0; i < 10; i++) {
            Recipe recipe = recipeRestController.createRecipe(
                new Recipe(null, RecipeTitleDataset.title(), RecipeDescriptionDataset.description())
            ).getBody();
            addSteps(recipe);
        }

        Recipe recipe = recipeRestController.getRandomRecipe().getBody();
        if (recipe != null) {
            System.out.println("Random recipe: " + recipe.toString() + "\n" + recipe.stepsToString());
            recipeRestController.deleteRecipe(recipe.getId());
        }
        recipe = recipeRestController.getRandomRecipe().getBody();
        if (recipe != null) {
            System.out.println("Random recipe: " + recipe.toString() + "\n" + recipe.stepsToString());
            for (Food ingredient : recipe.getIngredients()) {
                System.out.println("ingredient: " + ingredient.getName());
            }
        }

    }

    private void addSteps(Recipe recipe) {
        for (int i = 0; i < new Random().nextInt(3, 12); i++) {
            Food food = recipeMealOrchestratorService.getRandomFood();
            Double ingredientAmount = new Random().nextLong(0,11) + 0.5;
            String directions = StepStatementDataset.statement();
//            System.out.println("\tSelected food:"  + food.getName() + " with ingredient amount: " + ingredientAmount);
//            System.out.println("\tSelected directions: " + directions);
            Step step = new Step(null, recipe, food, ingredientAmount, directions, null);
            food.addStep(step);
            recipe.addStep(step);
            recipeMealOrchestratorService.saveStep(step);
            recipeMealOrchestratorService.updateFood(food.getId(), food);
            recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);

            System.out.println("\tAdding step to recipe:" + step + " ingredient:" + step.getIngredient().getName() + " recipe:" + step.getRecipe().getTitle());
        }
        recipeRestController.updateRecipe(recipe.getId(), recipe);
        System.out.println("\tLeaving addSteps...");
    }
}
