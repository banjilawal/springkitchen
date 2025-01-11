package com.lawal.banji.springkitchen.search;

import com.lawal.banji.springkitchen.food.Food;
import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.orchestrator.RecipeMealServiceHelper;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import com.lawal.banji.springkitchen.food.FoodValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecipeSearchService {

    private final RecipeMealServiceHelper recipeMealServiceHelper;

    @Autowired
    public RecipeSearchService(RecipeMealServiceHelper recipeMealServiceHelper, FoodValidator foodValidator) {
        this.recipeMealServiceHelper = recipeMealServiceHelper;
    }

    /* Global Search */
    @Transactional(readOnly = true)
    public Set<Recipe> search(String string) {
        Set<Recipe> matches = searchRecipes(string);
        for (Food food : searchIngredients(string)) {
            matches.addAll(food.getRecipes());
        }
        for(Step step : searchSteps(string)) {
            matches.add(step.getRecipe());
        }
        return matches;
    }

    /* Recipe Search Methods */
    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long id) { return recipeMealServiceHelper.findRecipeById(id); }

    @Transactional(readOnly = true)
    public Recipe findRecipeByTitle(String title) { return recipeMealServiceHelper.findRecipeByTitle(title); }

    @Transactional(readOnly = true)
    public Recipe findRecipeByDescription(String description) { return recipeMealServiceHelper.findRecipeByDescription(description); }

    @Transactional(readOnly = true)
    public List<Recipe> findAllRecipes () { return recipeMealServiceHelper.findAllRecipes(); }

    @Transactional(readOnly = true)
    public Set<Recipe> searchRecipes (String string) { return recipeMealServiceHelper.searchRecipes(string); }

    /* Food Search Methods */
    @Transactional(readOnly = true)
    public Food findIngredientById(Long id) { return recipeMealServiceHelper.findIngredientById(id); }

    @Transactional(readOnly = true)
    public Food findIngredientByName(String name) { return recipeMealServiceHelper.findIngredientByName(name); }

    @Transactional(readOnly = true)
    public List<Food> findAllIngredients() { return recipeMealServiceHelper.findAllIngredients(); }

    @Transactional(readOnly = true)
    public Set<Food> searchIngredients(String string) { return recipeMealServiceHelper.searchIngredients(string); }

    /* Step Search Methods */
    @Transactional(readOnly = true)
    public Step findStepById(Long id) { return recipeMealServiceHelper.findStepById(id); }

    @Transactional(readOnly = true)
    public List<Step> findAllSteps () { return recipeMealServiceHelper.findAllSteps(); }

    @Transactional(readOnly = true)
    public Set<Step> searchSteps (String string) { return recipeMealServiceHelper.searchSteps(string); }

    /* Combined Search Methods & Filters */
    @Transactional(readOnly = true)
    public Set<Recipe> selectRecipeByIngredient(Food food) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeMealServiceHelper.findAllRecipes()) {
            if (recipe.getIngredients().contains(food) ) matches.add(recipe);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Recipe> selectRecipeByMeal(Meal meal) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeMealServiceHelper.findAllRecipes()) {
            if (recipe.getMeals().contains(meal)) matches.add(recipe);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Recipe> selectRecipeByIngredientAndMeal(Food food, Meal meal) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeMealServiceHelper.findAllRecipes()) {
            if (recipe.getMeals().contains(meal) && recipe.filterStepsByIngredient(food).isEmpty()) matches.add(recipe);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Food> selectIngredientByRecipe(Recipe recipe) {
        Set<Food> matches = new HashSet<>();
        for (Food food : recipeMealServiceHelper.findAllIngredients()) {
            if (food.getRecipes().contains(recipe)) matches.add(food);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Food> selectIngredientByMeal(Meal meal) {
        Set<Food> matches = new HashSet<>();
        for (Food food : recipeMealServiceHelper.findAllIngredients()) {
            if (food.getMeals().contains(meal)) matches.add(food);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Step> selectStepByRecipe(Recipe recipe) {
        Set<Step> matches = new HashSet<>();
        for (Step step : recipeMealServiceHelper.findAllSteps()) {
            if (step.getRecipe().equals(recipe)) matches.add(step);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Step> selectStepByIngredient(Food food) {
        Set<Step> matches = new HashSet<>();
        for (Step step : recipeMealServiceHelper.findAllSteps()) {
            if (step.getIngredient().equals(food)) matches.add(step);
        }
        return matches;
    }
}
