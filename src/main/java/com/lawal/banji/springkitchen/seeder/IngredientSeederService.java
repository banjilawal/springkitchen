package com.lawal.banji.springkitchen.seeder;

import com.lawal.banji.springkitchen.dataset.FoodNameDataset;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientSeederService {

    @Autowired
    private FoodService foodService;

    @Autowired
    public IngredientSeederService(FoodService foodService) {
        this.foodService = foodService;
    }

    public void seed(int numberOfIngredients) {
        if (numberOfIngredients <= 0) throw new IllegalArgumentException("Number of ingredients must be greater than 0");
        String name = FoodNameDataset.name();
        for (int i = 0; i < numberOfIngredients; i++) {
            while (foodService.findByName(name) != null) name = FoodNameDataset.name();
            Food food = new Food(null, name);
            foodService.save(food);
        }
    }
}
