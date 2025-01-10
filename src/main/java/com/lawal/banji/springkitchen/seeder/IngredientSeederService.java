package com.lawal.banji.springkitchen.seeder;

import com.lawal.banji.springkitchen.dataset.FoodNameDataset;
import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import com.lawal.banji.springkitchen.recipe.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientSeederService {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    public IngredientSeederService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void seed(int numberOfIngredients) {
        if (numberOfIngredients <= 0) throw new IllegalArgumentException("Number of ingredients must be greater than 0");
        String name = FoodNameDataset.name();
        for (int i = 0; i < numberOfIngredients; i++) {
            while (ingredientService.findByName(name) != null) name = FoodNameDataset.name();
            Ingredient ingredient = new Ingredient(null, name);
            ingredientService.save(ingredient);
        }
    }
}
