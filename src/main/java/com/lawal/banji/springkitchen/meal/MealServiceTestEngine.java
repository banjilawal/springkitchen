//package com.lawal.banji.springkitchen.meal;
//
//import com.lawal.banji.springkitchen.meal.model.Meal;
//import com.lawal.banji.springkitchen.meal.service.MealService;
//import com.lawal.banji.springkitchen.common.RandomPropertyService;
//import com.lawal.banji.springkitchen.common.CreationService;
//import com.lawal.banji.springkitchen.common.RandomSelectionService;
//import com.lawal.banji.springkitchen.recipe.service.RecipeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//@Order(1)
//public class MealServiceTestEngine implements CommandLineRunner {
//
//    public static final int NUMBER_OF_MEALS = 54;
//
//    @Autowired
//    private CreationService creationService;
//
//    @Autowired
//    private RandomSelectionService randomSelectionService;
//
//    @Autowired
//    RandomPropertyService randomPropertyService;
//
//    @Autowired
//    private RecipeService recipeService;
//
//    @Autowired
//    private MealService mealService;
//
//    @Autowired
//    private OrchestrationService orchestrationService;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        for (int i = 0; i < NUMBER_OF_MEALS; i++) {
//            Meal meal = creationService.createMeal(randomSelectionService.randomRecipe());
//            mealService.save(meal);
//        }
//        System.out.println("Starting MealServiceTestEngine...");
//
//        mealSavingTest();
//        mealDeletionTest();
//    }
//
//    private void mealSavingTest() {
//
//        System.out.println("========================================================================\n" +
//            "Running MealServiceTestEngine.mealSavingTest...");
//        Meal meal = creationService.createMeal(randomSelectionService.randomRecipe());
//        long preTestMealCount = mealService.count();
//
//        System.out.println("MealServiceTestEngine.mealSavingTest 1: Saving new meal item:" + meal.getId());
//        Meal savedMeal = mealService.save(meal);
//        long postTestMealCount = mealService.count();
//        String testResult1 = meal.equals(savedMeal) && preTestMealCount < postTestMealCount
//                ? "SUCCESS" : "FAILURE";
//        System.out.println("MealServiceTestEngine.mealSavingTest Result 1:" + testResult1);
//
//
//        meal = randomSelectionService.randomMeal();
//        System.out.println("MealServiceTestEngine.mealSavingTest 2: Resaving meal with id:" + meal.getId());
//        preTestMealCount = mealService.count();
//        savedMeal = mealService.save(meal);
//        postTestMealCount = mealService.count();
//        String testResult2 = savedMeal.equals(meal) && preTestMealCount == postTestMealCount ? "SUCCESS" : "FAILURE";
//        System.out.println("MealServiceTestEngine.mealSavingTest Result 2:" + testResult2);
//
//        LocalDateTime originalServingTime = meal.getServedAt();
//        LocalDateTime newServingTime = randomPropertyService.timestamp(CreationService.DATE_TIME_FLOOR, CreationService.DATE_TIME_CEILING);
//        System.out.println("MealServiceTestEngine.mealSavingTest 3: Using saving instead of update after changing serving time" +
//            " from:" + originalServingTime + " to:" + newServingTime);
//        preTestMealCount = mealService.count();
//        savedMeal = mealService.save(meal);
//        postTestMealCount = mealService.count();
//        String testResult3 = !meal.getServedAt().equals(originalServingTime) ? "SUCCESS" : "FAILURE";
//        System.out.println("MealServiceTestEngine.mealSavingTest Result 3:" + testResult3);
//
//        System.out.println("========================================================================\n" +
//            "Completed MealServiceTestEngine.mealSavingTest...");
//    }
//
//    private void mealDeletionTest() {
//
//        System.out.println("========================================================================\n" +
//            "Running MealServiceTestEngine.mealDeletionTest...");
//
//        long preTestMealCount = mealService.count();
//        Meal meal = randomSelectionService.randomMeal();
//        System.out.println("MealServiceTestEngine.mealDeletionTest 1: Deleting meal:" + meal.getId());
//
//        mealService.deleteById(meal.getId());
//        long postTestMealCount = mealService.count();
//        String testResult1 = preTestMealCount < postTestMealCount ? "SUCCESS" : "FAILURE";
//        System.out.println("MealServiceTestEngine.mealSavingTest Result 1:" + testResult1);
//
//        Long id = randomSelectionService.randomMeal().getId();
//        mealService.deleteById(id);
//        String testResult2 = mealService.findById(id) == null? "SUCCESS" : "FAILURE";
//        System.out.println("MealServiceTestEngine.mealSavingTest: Finding deleted meal with id " + id
//            + ": + testResult2:" + testResult2);
//
//        System.out.println("========================================================================\n" +
//            "Completed MealServiceTestEngine.mealSavingTest...");
//    }
//}