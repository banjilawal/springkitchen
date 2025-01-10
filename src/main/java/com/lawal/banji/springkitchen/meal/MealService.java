package com.lawal.banji.springkitchen.meal;

import com.lawal.banji.springkitchen.recipe.model.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MealService {

    @Autowired
    private MealRepo mealRepo;

    @Autowired
    public MealService(MealRepo mealRepo) {
        this.mealRepo = mealRepo;
    }

    public Long count() { return mealRepo.count(); }

    @Transactional
    public Meal save(Meal meal) {
        if (meal == null) { throw new IllegalArgumentException("mealRepo.save() Meal cannot be save a null meal"); }
        if (meal.getId() == null || findById(meal.getId()) == null) {
            if (meal.getRecipe() != null) meal.getRecipe().addMeal(meal);
        }
        return mealRepo.save(meal);
    }

    public Meal findById(Long id) {
        return mealRepo.findById(id).orElse(null);
    }

    public List<Meal> findAll() { return mealRepo.findAll(); }

    @Transactional
    public Recipe deleteById(Long id) {
        if (id == null) { throw new IllegalArgumentException("mealRepo.deleteById() received a null mealId. Meal ID cannot be null"); }
        Recipe recipe = null;
        Meal meal = findById(id);
        if (meal == null) {
            System.out.println("Meal not found with ID: " + id);
        } else {
            recipe = meal.getRecipe();
            if (recipe != null) {
                recipe.removeMeal(meal);
                meal.setRecipe(recipe);
            }
            mealRepo.deleteById(id);
        }
        return recipe;
    }


}