//package com.lawal.banji.springkitchen;
//
//import com.lawal.banji.springkitchen.recipe.service.PantryIngredientSearchService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RecipeServiceCommandLineTester implements CommandLineRunner {
//
//    private final PantryIngredientSearchService recipeService;
//
//    public RecipeServiceCommandLineTester(PantryIngredientSearchService recipeService) {
//        this.recipeService = recipeService;
//    }
//
//    @Override
//    public void run(String... args) {
//        int minSteps = 1; // Change according to your testing needs
//        int maxSteps = 5;
//
//        System.out.println("Testing combineMetadataAndSteps with minSteps: " + minSteps + ", maxSteps: " + maxSteps);
//
//
//        System.out.println("combineMetadataAndSteps executed successfully.");
//    }
//}