package com.lawal.banji.springkitchen.recipe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeMealOrchestratorService recipeMealOrchestratorService;
    private final RecipeSearchService recipeSearchService;

    // Constructor-based dependency injection for the services
    public RecipeController(RecipeMealOrchestratorService recipeMealOrchestratorService,
                            RecipeSearchService recipeSearchService) {
        this.recipeMealOrchestratorService = recipeMealOrchestratorService;
        this.recipeSearchService = recipeSearchService;
    }

    // Get all recipes
    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeMealOrchestratorService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    // Get a single recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        RecipeDto recipe = recipeMealOrchestratorService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(recipe);
    }

    // Search recipes based on query (e.g., name, description, ingredients)
    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> searchRecipes(@RequestParam String query) {
        List<RecipeDto> recipes = recipeSearchService.searchRecipes(query);
        return ResponseEntity.ok(recipes);
    }

    // Create a new recipe
    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto createdRecipe = recipeMealOrchestratorService.createRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRecipe);
    }

    // Update an existing recipe
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(
            @PathVariable Long id,
            @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipe = recipeMealOrchestratorService.updateRecipe(id, recipeDto);
        if (updatedRecipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedRecipe);
    }

    // Delete a recipe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean deleted = recipeMealOrchestratorService.deleteRecipe(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }

    // Find meals associated with a specific recipe ID
    @GetMapping("/{id}/meals")
    public ResponseEntity<List<MealDto>> getMealsForRecipe(@PathVariable Long id) {
        List<MealDto> meals = recipeMealOrchestratorService.getMealsForRecipe(id);
        if (meals == null || meals.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(meals);
    }
}