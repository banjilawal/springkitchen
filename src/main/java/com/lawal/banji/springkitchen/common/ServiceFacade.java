package com.lawal.banji.springkitchen.common;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.meal.service.MealService;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.step.service.StepService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Service
public class ServiceFacade {

    private final StepService stepService;
    private final MealService mealService;
    private final RecipeService recipeService;
    private final FoodService foodService;


    @Autowired
    public ServiceFacade (
        FoodService foodService,
        StepService stepService,
        MealService mealService,
        RecipeService recipeService
    ) {
        this.foodService = foodService;
        this.stepService = stepService;
        this.mealService = mealService;
        this.recipeService = recipeService;
    }

    /* RecipeService Operations */

    // Recipe Creation
    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        return recipeService.save(recipe);
    }

    // Recipe Reading
    @Transactional(readOnly = true)
    public Long countRecipes() { return recipeService.count(); }

    @Transactional(readOnly = true)
    public Recipe findRecipeById(Long id) { return recipeService.findById(id); }

    @Transactional(readOnly = true)
    public Recipe findRecipeByTitle(String title) { return recipeService.findByTitle(title); }

    @Transactional(readOnly = true)
    public Recipe findRecipeByDescription(String description) { return recipeService.findByDescription(description); }

    @Transactional(readOnly = true)
    public List<Recipe> findAllRecipes () { return recipeService.findAll(); }

    @Transactional(readOnly = true)
    public Set<Recipe> searchRecipes (String string) { return recipeService.search(string); }

    // Recipe Updating
    @Transactional
    public Recipe updateRecipe(Long targetId, Recipe source) { return recipeService.update(targetId, source); }

    // Recipe Deleting
    @Transactional
    public void deleteRecipeById(Long id) { recipeService.deleteById(id); }

    /* FoodService Operations */

    // Food Creating
    @Transactional
    public Food saveFood(Food food) {
        AppLogger.debug(ServiceFacade.class, "saveFood() called with: food = [" + food + "]");
        return foodService.save(food);
    }

    // Food Reading
    @Transactional(readOnly = true)
    public Long countFoods() { return foodService.count(); }

    @Transactional(readOnly = true)
    public Food findFoodById(Long id) { return foodService.findById(id); }

    @Transactional(readOnly = true)
    public Food findFoodByName(String name) { return foodService.findByName(name); }

    @Transactional(readOnly = true)
    public List<Food> findAllFoods() { return foodService.findAll(); }

    @Transactional(readOnly = true)
    public Set<Food> searchFoods(String string) { return foodService.search(string); }

//    @Transactional(readOnly = true)
//    public Food getRandomFood() { return foodService.randomFood(); }

    // Food Updating
    @Transactional
    public Food updateFood(Long targetId, Food source) {
        return foodService.update(targetId, source);
    }

    // Food Deleting
    @Transactional
    public void deleteFoodById(Long id) { foodService.deleteById(id); }

    // Food Generating
    public Food generateFood() { return foodService.generateFood(); }

    /* StepService Operations */

    // Step Creating
    @Transactional
    public Step saveStep(Step step) { return stepService.save(step); }

    // Step Reading
    @Transactional(readOnly = true)
    public Long countSteps() { return stepService.count(); }

    @Transactional(readOnly = true)
    public Step findStepById(Long id) { return stepService.findById(id); }

    @Transactional(readOnly = true)
    public List<Step> findAllSteps () { return stepService.findAll(); }

    @Transactional(readOnly = true)
    public Set<Step> searchSteps (String string) { return stepService.search(string); }

    @Transactional(readOnly = true)
    public Step getRandomStep() { return stepService.randomStep(); }

    // Step Updating
    @Transactional
    public Step updateStep(Long targetId, Step source) { return stepService.update(targetId, source); }

    // Step Deleting
    @Transactional
    public void deleteStepById(Long id) { stepService.deleteById(id); }

    /* MealService Operations */

    // Recipe Reading

    @Transactional
    public Meal saveMeal(Meal meal) { return mealService.save(meal);}

    @Transactional
    public void deleteMealById(Long id) { mealService.deleteById(id); }

    @Transactional(readOnly = true)
    public Long countMeals() { return mealService.count(); }

    @Transactional(readOnly = true)
    public Meal findMealById(Long id) { return mealService.findById(id); }

    @Transactional(readOnly = true)
    public List<Meal> findAllMeals () { return mealService.findAll(); }

}