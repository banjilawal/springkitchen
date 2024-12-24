package com.lawal.banji.springkitchen.recipe.service;

import com.lawal.banji.springkitchen.recipe.data.RecipeRepo;
import com.lawal.banji.springkitchen.recipe.dto.RecipeDTO;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeService {

    @Resource
    private RecipeRepo recipeRepo;

    @Autowired
    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Long count() {
        return recipeRepo.count();
    }

    public Recipe save(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.update(recipeDTO);
        return recipeRepo.save(recipe);
    }

    public Recipe update(Long targetId, RecipeDTO recipeDTO) {
        Recipe recipe = findById(targetId);
        if (recipe == null) return null;
        recipe.update(recipeDTO);
        return recipeRepo.save(recipe);
    }

    public Iterable<Recipe> findAll() {
        return recipeRepo.findAll();
    }

    public Recipe findById(Long id) {
        return recipeRepo.findById(id).orElse(null);
    }

    public Recipe findByTitle (String title) {
        return recipeRepo.findByTitle(title);
    }

    public void deleteById(Long id) {
        recipeRepo.deleteById(id);
    }

    public Set<Recipe> searchByTitle (String string) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeRepo.findAll()) {
            if (recipe.getTitle().toLowerCase().contains(string.toLowerCase()))
                matches.add(recipe);
        }
        return matches;
    }

    public Set<Recipe> searchByDescription (String string) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeRepo.findAll()) {
            if (recipe.getDescription().toLowerCase().contains(string.toLowerCase()))
                matches.add(recipe);
        }
        return matches;
    }

    public Set<Recipe> searchByStepDirections (String string) {
        Set<Recipe> matches = new HashSet<>();
        for (Recipe recipe : recipeRepo.findAll()) {
            Set<Step> steps = recipe.searchStepDirections(string);
            if (!steps.isEmpty()) matches.add(recipe);
        }
        return matches;
    }

    public Set<Recipe> search (String string) {
        Set<Recipe> matches = new HashSet<>();
        matches.addAll(searchByTitle(string));
        matches.addAll(searchByDescription(string));
        matches.addAll(searchByStepDirections(string));
        return matches;
    }
}
