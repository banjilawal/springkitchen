package com.lawal.banji.springkitchen.search;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.common.ServiceFacade;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@org.springframework.stereotype.Service
public class RecipeSearchService {

    private final ServiceFacade serviceFacade;

    @Autowired
    public RecipeSearchService(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    /* Global Search */
    @Transactional(readOnly = true)
    public Set<Recipe> search(String string) {
        Set<Recipe> matches = searchRecipes(string);
        for (Food food : searchFoods(string)) {
            matches.addAll(food.getRecipes());
        }
        for(Step step : searchSteps(string)) {
            matches.add(step.getRecipe());
        }
        return matches;
    }

    /* Recipe Search Methods */
    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long id) { return serviceFacade.findRecipeById(id); }

    @Transactional(readOnly = true)
    public Recipe findRecipeByTitle(String title) { return serviceFacade.findRecipeByTitle(title); }

    @Transactional(readOnly = true)
    public Recipe findRecipeByDescription(String description) { return serviceFacade.findRecipeByDescription(description); }

    @Transactional(readOnly = true)
    public List<Recipe> findAllRecipes () { return serviceFacade.findAllRecipes(); }

    @Transactional(readOnly = true)
    public Set<Recipe> searchRecipes (String string) { return serviceFacade.searchRecipes(string); }

    /* Food Search Methods */
    @Transactional(readOnly = true)
    public Food findFoodById(Long id) { return serviceFacade.findFoodById(id); }

    @Transactional(readOnly = true)
    public Food findFoodByName(String name) { return serviceFacade.findFoodByName(name); }

    @Transactional(readOnly = true)
    public List<Food> findAllFoods() { return serviceFacade.findAllFoods(); }

    @Transactional(readOnly = true)
    public Set<Food> searchFoods(String string) { return serviceFacade.searchFoods(string); }

    /* Step Search Methods */
    @Transactional(readOnly = true)
    public Step findStepById(Long id) { return serviceFacade.findStepById(id); }

    @Transactional(readOnly = true)
    public List<Step> findAllSteps () { return serviceFacade.findAllSteps(); }

    @Transactional(readOnly = true)
    public Set<Step> searchSteps (String string) { return serviceFacade.searchSteps(string); }

    /* Combined Search Methods & Filters */
    @Transactional(readOnly = true)
    public Set<Recipe> selectRecipeByIngredient(Food food) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : serviceFacade.findAllRecipes()) {
            if (recipe.getIngredients().contains(food) ) matches.add(recipe);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Recipe> selectRecipeByMeal(Meal meal) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : serviceFacade.findAllRecipes()) {
            if (recipe.getMeals().contains(meal)) matches.add(recipe);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Recipe> selectRecipeByIngredientAndMeal(Food food, Meal meal) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : serviceFacade.findAllRecipes()) {
            if (recipe.getMeals().contains(meal) && recipe.filterStepsByIngredient(food).isEmpty()) matches.add(recipe);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Food> selectIngredientByRecipe(Recipe recipe) {
        Set<Food> matches = new HashSet<>();
        for (Food food : serviceFacade.findAllFoods()) {
            if (food.getRecipes().contains(recipe)) matches.add(food);
        }
        return matches;
    }
//
//    @Transactional(readOnly = true)
//    public Set<Food> selectFoodByMeal(Meal meal) {
//        Set<Food> matches = new HashSet<>();
//        for (Food food : recipeMealServiceHelper.findAllRecipes()) {
//            if (food.getMeals().contains(meal)) matches.add(food);
//        }
//        return matches;
//    }

    @Transactional(readOnly = true)
    public Set<Step> selectStepByRecipe(Recipe recipe) {
        Set<Step> matches = new HashSet<>();
        for (Step step : serviceFacade.findAllSteps()) {
            if (step.getRecipe().equals(recipe)) matches.add(step);
        }
        return matches;
    }

    @Transactional(readOnly = true)
    public Set<Step> selectStepByIngredient(Food food) {
        Set<Step> matches = new HashSet<>();
        for (Step step : serviceFacade.findAllSteps()) {
            if (step.getIngredient().equals(food)) matches.add(step);
        }
        return matches;
    }
}