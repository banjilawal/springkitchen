package com.lawal.banji.springkitchen.food;

import com.lawal.banji.springkitchen.dataset.FoodNameDataset;
import com.lawal.banji.springkitchen.dataset.RecipeDescriptionDataset;
import com.lawal.banji.springkitchen.dataset.RecipeTitleDataset;
import com.lawal.banji.springkitchen.dataset.StepStatementDataset;
import com.lawal.banji.springkitchen.food.controller.FoodRestController;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealOrchestratorService;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@Component
@Order(1)
public class FoodApplicationRunner implements CommandLineRunner {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodRestController foodRestController;

    @Autowired
    private RecipeMealOrchestratorService recipeMealOrchestratorService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting FoodApplicationRunner...");

        // Test FoodService
//        testFoodService();

        // Test FoodRestController
        testFoodService();
        testRecipeService();

        System.out.println("Deleting steps with null recipe and null ingredient...");
        int deletedSteps = 0;
        for (Step step : recipeMealOrchestratorService.findAllSteps()) {
            if (step.getRecipe() == null || step.getIngredient() == null) {
                System.out.println("deleting step " + step.getId() + " directions:" + step.getDirections());
                recipeMealOrchestratorService.deleteStep(step.getId());
                deletedSteps++;
            }
        }
        System.out.println("Deleted " + deletedSteps + " steps");
        System.out.println("Finished FoodApplicationRunner...");
    }

    private void testFoodService() {
        System.out.println("Testing recipeMealOrchestrator.FoodService...");
        Food food;
        // Example: Create Food via Controller
        for (int i = 0; i < 100; i++) {
            food = recipeMealOrchestratorService.saveFood(new Food(null, FoodNameDataset.name()));
            System.out.println("Created food via orchestrator:" + food);
        }
        food = recipeMealOrchestratorService.getRandomFood();
        Long foodId = food.getId();

        System.out.println("Retrieved food via orchestrator:" + recipeMealOrchestratorService.findFoodById(foodId));

        System.out.println("recipeMealOrchestrator deleted food with id:" + foodId + " details:" + recipeMealOrchestratorService.findFoodById(foodId));
        recipeMealOrchestratorService.deleteFood(foodId);

        food = recipeMealOrchestratorService.getRandomFood();
        foodId = food.getId();
        String oldFoodName = food.getName();
        recipeMealOrchestratorService.updateFood(foodId, new Food(null, FoodNameDataset.name()));
        System.out.println("recipeMealOrchestrator updated food with id old name:" + oldFoodName);
        System.out.println(" updated food details:" + recipeMealOrchestratorService.findFoodById(foodId));


        String searchString = "be";
        System.out.println("Finding foods with name containing '" + searchString + "'");
        recipeMealOrchestratorService.searchFoods(searchString).forEach(System.out::println);
    }

    public void testRecipeService() {
        System.out.println("Testing recipeMealOrchestrator.RecipeService...");
        Recipe recipe;
        for (int i = 0; i < 20; i++) {
            recipe = recipeMealOrchestratorService.saveRecipe(new Recipe(null, RecipeTitleDataset.title(), RecipeDescriptionDataset.description()));
            addSteps(recipe);
            System.out.println("Created recipe via orchestrator:" + recipeMealOrchestratorService.findRecipeById(recipe.getId()));
        }

        System.out.println("All Recipes via Orchestrator Service:");
        recipeMealOrchestratorService.findAllRecipes().forEach(System.out::println);

        recipe = recipeMealOrchestratorService.getRandomRecipe();
        System.out.println("Deleting recipe with id:" + recipe.getId());
        recipeMealOrchestratorService.deleteRecipe(recipe.getId());

        recipe = recipeMealOrchestratorService.getRandomRecipe();
        Long recipeId = recipe.getId();
        String oldTitle = recipe.getTitle();
        String oldDescription = recipe.getDescription();
        recipeMealOrchestratorService.updateRecipe(recipeId, new Recipe(null, RecipeTitleDataset.title(), RecipeDescriptionDataset.description()));
        System.out.println("Updated Recipe old title:" + oldTitle + " old description:" + oldDescription);
        System.out.println("updated recipe current info:" + recipeMealOrchestratorService.findRecipeById(recipeId).toString());

        String searchString = "butter";
        System.out.println("Finding recipes whose titles or descriptions contain the word '" + searchString + "'");
        recipeMealOrchestratorService.searchRecipes(searchString).forEach(System.out::println);
    }

    private void addSteps(Recipe recipe) {
        for (int i = 0; i < new Random().nextInt(2, 12); i++) {
            Food food = recipeMealOrchestratorService.getRandomFood();
            Double ingredientAmount = new Random().nextLong(0, 11) + 0.5;
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
        recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);
        System.out.println("\tLeaving addSteps...");
    }
}
