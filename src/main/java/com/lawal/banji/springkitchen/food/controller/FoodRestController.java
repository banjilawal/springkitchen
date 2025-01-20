package com.lawal.banji.springkitchen.food.controller;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealOrchestratorService;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealServiceHelper;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/food")
public class FoodRestController {


    private final RecipeMealServiceHelper recipeMealServiceHelper;
    private final RecipeMealOrchestratorService recipeMealOrchestratorService;

    @Autowired
    public FoodRestController(RecipeMealServiceHelper recipeMealServiceHelper, RecipeMealOrchestratorService recipeMealOrchestratorService) {
        this.recipeMealServiceHelper = recipeMealServiceHelper;
        this.recipeMealOrchestratorService = recipeMealOrchestratorService;
    }

    /* Create methods*/
    @PostMapping
    public ResponseEntity<Food> createFood(Food food) {
        Food createdFood = recipeMealOrchestratorService.saveFood(food);
        return ResponseEntity.ok(createdFood);
    }

    /* Read Methods */
    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Food food = recipeMealOrchestratorService.findFoodById(id);
        return ResponseEntity.ok(food);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Food> getFoodByName(@PathVariable String name) {
        Food food = recipeMealOrchestratorService.findFoodByName(name);
        return ResponseEntity.ok(food);
    }

    @GetMapping
    public ResponseEntity<Iterable<Food>> getAllFoods() {
        Iterable<Food> foods = recipeMealServiceHelper.findAllFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Food>> searchFoods(String string) {
        Iterable<Food> foods = recipeMealOrchestratorService.searchFoods(string);
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}/recipes")
    public ResponseEntity<Iterable<Recipe>> getRecipesByFoodId(@PathVariable Long id) {
        Iterable<Recipe> recipes = recipeMealOrchestratorService.findFoodById(id).getRecipes();
        return ResponseEntity.ok(recipes);
    }

    /* Update Methods */
    @GetMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, Food source) {
        Food food = recipeMealOrchestratorService.updateFood(id, source);
        return ResponseEntity.ok(food);
    }

    /* Delete Methods */
    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        recipeMealOrchestratorService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}
