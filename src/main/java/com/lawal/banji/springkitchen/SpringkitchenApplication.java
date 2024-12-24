package com.lawal.banji.springkitchen;

import com.lawal.banji.springkitchen.pantry.data.PantryItemDTO;
import com.lawal.banji.springkitchen.pantry.data.PantryItemGenerator;
import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import com.lawal.banji.springkitchen.pantry.service.PantryItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringkitchenApplication {

    public static final int NUMBER_OF_PANTRY_ITEMS = 15;
    public static final int NUMBER_OF_RECIPES = 10;

    public static final int MIN_STEPS = 3;
    public static final int MAX_STEPS = 10;

    public static final Long  MIN_REORDER_LEVEL = 10L;
    public static final Long  MAX_REORDER_LEVEL = 25L;




    public static void main(String[] args) {
        SpringApplication.run(SpringkitchenApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedPantryItems(PantryItemService pantryItemService) {
        return args -> {
            for (PantryItemDTO pantryItemDTO : PantryItemGenerator.pantryItemDTOs(
                NUMBER_OF_PANTRY_ITEMS,
                MIN_REORDER_LEVEL, MAX_REORDER_LEVEL)
            ) {
                pantryItemService.save(pantryItemDTO);
            }
//            pantryItemService.toString();
//            for (PantryItem pantryItem : pantryItemService.findAll()) {
//                System.out.println(pantryItem.toString());
//            }
        };
    }

//    @Bean
//    public CommandLineRunner seedRecipes (RecipeService recipeService) {
//        return args -> {
//            for (Recipe recipe : RecipeGenerator.recipes(NUMBER_OF_RECIPES, MIN_STEPS, MAX_STEPS)) {
//                recipeService.save(recipe);
//            }
//            for (Recipe recipe : recipeService.findAll()) {
//                System.out.println(recipe.toString());
//            }
//        };
//    }
}
