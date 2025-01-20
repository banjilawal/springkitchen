package com.lawal.banji.springkitchen.recipe.controller;

import com.lawal.banji.springkitchen.global.AppLogger;
import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealOrchestratorService;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.service.RecipeServiceLoggingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeRestController {

    private final RecipeMealOrchestratorService recipeMealOrchestratorService;

    @Autowired
    public RecipeRestController(RecipeMealOrchestratorService recipeMealOrchestratorService) {
        this.recipeMealOrchestratorService = recipeMealOrchestratorService;
    }

    // Create a new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe createdRecipe = recipeMealOrchestratorService.saveRecipe(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    // Get all recipes
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        AppLogger.debug(RecipeRestController.class, RecipeServiceLoggingMessage.FETCHING_ALL_RECIPES_MESSAGE);
        List<Recipe> recipes = recipeMealOrchestratorService.findAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    // Get a single recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        AppLogger.debug(RecipeRestController.class, RecipeServiceLoggingMessage.FINDING_RECIPE_BY_ID_MESSAGE);
        Recipe recipe = recipeMealOrchestratorService.findRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(recipe);
    }

    // Search recipes based on query (e.g., name, description, ingredients)
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String query) {
        List<Recipe> recipes = recipeMealOrchestratorService.searchRecipes(query);
        return ResponseEntity.ok(recipes);
    }

    // Random recipe
    @GetMapping("/random")
    public ResponseEntity<Recipe> getRandomRecipe() {
        Recipe recipe = recipeMealOrchestratorService.getRandomRecipe();
        if (recipe == null) { return ResponseEntity.noContent().build(); }
        return ResponseEntity.ok(recipe);
    }

    // Update an existing recipe
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe source) {
        Recipe updatedRecipe = recipeMealOrchestratorService.updateRecipe(id, source);
        if (updatedRecipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedRecipe);
    }

    // Delete a recipe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeMealOrchestratorService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    // Find meals associated with a specific recipe ID
    @GetMapping("/{id}/meals")
    public ResponseEntity<List<Meal>> getMealsForRecipe(@PathVariable Long id) {
        List<Meal> meals = recipeMealOrchestratorService.findRecipeById(id).getMeals();
        if (meals == null || meals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(meals);
    }
}