package com.lawal.banji.springkitchen.meal.service;

import com.lawal.banji.springkitchen.meal.MealRepo;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MealService {

    private final MealRepo mealRepo;

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

    @Transactional
    public Meal update (Long targetId, Meal source) {
        Meal target = findById(targetId);
        if (target != null) {
            target.getUpdate(source);
            return save(target);
        }
        return null;
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

    public List<Meal> filterByRecipe(Recipe recipe) {
        List<Meal> matches = new ArrayList<>();

        if (recipe == null) return matches;

        for (Meal meal : findAll()) {
            if (meal.getRecipe() != null && meal.getRecipe().equals(recipe) && !matches.contains(meal)) matches.add(meal);
        }
        return matches;
    }

    public List<Meal> filterByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        List<Meal> matches = new ArrayList<>();

        if (startTime == null || endTime == null) return matches;

        for (Meal meal : findAll()) {
            if (meal.isInDateRange(startTime, endTime) && !matches.contains(meal)) matches.add(meal);
        }
        return matches;
    }


}