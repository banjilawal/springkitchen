//package com.lawal.banji.springkitchen.recipe.service;
//
//import com.lawal.banji.springkitchen.pantry.model.PantryItem;
//import com.lawal.banji.springkitchen.pantry.service.PantryItemService;
//import com.lawal.banji.springkitchen.recipe.model.Ingredient;
//import com.lawal.banji.springkitchen.recipe.data.IngredientRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//import java.util.Optional;
//
//@Service
//public final class IngredientService {
//
//    private IngredientRepo ingredientRepo;
//    private PantryItemService pantryItemService;
//
//
//    @Autowired
//    public IngredientService(IngredientRepo ingredientRepo, PantryItemService pantryItemService) {
//        this.ingredientRepo = ingredientRepo;
//        this.pantryItemService = pantryItemService;
//    }
//
//    public IngredientRepo getIngredientRepo() {
//        return ingredientRepo;
//    }
//
//    public PantryItemService getPantryItemService() {
//        return pantryItemService;
//    }
//
//    public void setPantryItemService(PantryItemService pantryItemService) {
//        this.pantryItemService = pantryItemService;
//    }
//
//    @Autowired
//    public void setIngredientRepo(IngredientRepo ingredientRepo) {
//        this.ingredientRepo = ingredientRepo;
//    }
//
//    public Ingredient save (Ingredient ingredient) {
//        return ingredientRepo.save(ingredient);
//    }
//
//    public void deleteById(Long id) {
//        ingredientRepo.deleteById(id);
//    }
//
//    public Iterable<Ingredient> findAll() {
//        return ingredientRepo.findAll();
//    }
//
//    public Ingredient findById(Long id) {
//        return ingredientRepo.findById(id).orElse(null);
//    }
//
//    public Ingredient findByName(String name) {
//        for (Ingredient ingredient: ingredientRepo.findAll()) {
//            if (ingredient.getPantryItem().getName().equalsIgnoreCase(name))
//                return ingredient;
//        }
//        return null;
//    }
//
//    public Iterable<Ingredient> search (String name) {
//        HashSet<Ingredient> matches = new HashSet<>();
//        for (Ingredient ingredient: ingredientRepo.findAll()) {
//            if (ingredient.getPantryItem().getName().toLowerCase().contains(name.toLowerCase()))
//                matches.add(ingredient);
//        }
//        return matches;
//    }
//
//    public Iterable<PantryItem> searchPantry (String name) {
//        return pantryItemService.search(name);
//    }
//}
