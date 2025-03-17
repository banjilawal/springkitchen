//package com.lawal.banji.springkitchen.food;
//
//import com.lawal.banji.springkitchen.food.model.Food;
//import com.lawal.banji.springkitchen.food.service.FoodService;
//import com.lawal.banji.springkitchen.common.RandomPropertyService;
//import com.lawal.banji.springkitchen.common.CreationService;
//
//import com.lawal.banji.springkitchen.common.RandomSelectionService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Component
//@Order(1)
//public class FoodServiceTestEngine implements CommandLineRunner {
//
//    public static final int NUMBER_OF_FOODS = 20;
//
//    @Autowired
//    private FoodService foodService;
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
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        for (int i = 0; i < NUMBER_OF_FOODS; i++) {
//            foodService.save(creationService.createFood());
//        }
//        System.out.println("Starting FoodServiceTestEngine...");
//
//        System.out.println("Starting FoodService.save Tests......");
//        foodSavingTest();
//        System.out.println("Finished FoodService.save Tests......");
//
//        System.out.println("Starting FoodService.find Tests......");
//        foodFindingTest();
//        System.out.println("Finished FoodService.find Tests......");
//
//        System.out.println("Starting FoodService.update Tests......");
//        testUpdatingFood();
//        System.out.println("Finished FoodService.update Tests......");
//
//        System.out.println("Starting FoodService.delete Tests......");
//        testDeletingFood();
//        System.out.println("Finished FoodService.delete Tests......");
//
//
//
//        // Test FoodService
////        testFoodService();
//
//        // Test FoodRestController
////        testFoodService();
////        testRecipeService();
//
////        System.out.println("Deleting steps with null recipe and null ingredient...");
////        int deletedSteps = 0;
////        for (Step step : recipeMealOrchestratorService.findAllSteps()) {
////            if (step.getRecipe() == null || step.getIngredient() == null) {
////                System.out.println("deleting step " + step.getId() + " directions:" + step.getDirections());
////                recipeMealOrchestratorService.deleteStep(step.getId());
////                deletedSteps++;
////            }
////        }
////        System.out.println("Deleted " + deletedSteps + " steps");
//        System.out.println("Finished FoodServiceTestEngine...");
//    }
//
//    private void foodSavingTest() {
//
//        System.out.println("========================================================================\n" +
//            "Running FoodServiceTestEngine.foodSavingTest...");
//        Food food = creationService.createFood();
//        long preTestFoodCount = foodService.count();
//
//        System.out.println("FoodServiceTestEngine.foodSavingTest 1: Saving new food item:" + food.getName());
//        Food savedFood = foodService.save(food);
//        long postTestFoodCount = foodService.count();
//        String testResult1 =
//            savedFood.getName().equalsIgnoreCase(food.getName()) && preTestFoodCount < postTestFoodCount
//                ? "SUCCESS" : "FAILURE";
//        System.out.println("FoodServiceTestEngine.foodSavingTest Result 1:" + testResult1);
//
//
//        food = randomSelectionService.randomFood();
//        System.out.println(
//            "FoodServiceTestEngine.foodSavingTest 2: Saving food which already exists:"
//            + food.getName()
//        );
//        preTestFoodCount = foodService.count();
//        savedFood = foodService.save(food);
//        postTestFoodCount = foodService.count();
//        String testResult2 = savedFood.equals(food) && preTestFoodCount == postTestFoodCount ? "SUCCESS" : "FAILURE";
//        System.out.println("FoodServiceTestEngine.foodSavingTest Result 2:" + testResult2);
//
//
////        System.out.println("FoodServiceTestEngine.foodSavingTest 3: Saving food with empty name:");
////        preTestFoodCount = foodService.count();
////        savedFood = foodService.save(new Food(null, ""));
////        postTestFoodCount = foodService.count();
////        String testResult3 = savedFood == null && preTestFoodCount == postTestFoodCount ? "SUCCESS" : "FAILURE";
////        System.out.println("FoodServiceTestEngine.foodSavingTest Result 3:" + testResult3);
//
//        System.out.println("FoodServiceTestEngine.foodSavingTest 4: Saving food null food:");
//        preTestFoodCount = foodService.count();
//        savedFood = foodService.save(null);
//        postTestFoodCount = foodService.count();
//        String testResult4 = savedFood == null && preTestFoodCount == postTestFoodCount ? "SUCCESS" : "FAILURE";
//        System.out.println("FoodServiceTestEngine.foodSavingTest Result 4:" + testResult4);
//
//        System.out.println("========================================================================\n" +
//            "Completed FoodServiceTestEngine.foodSavingTest...");
//    }
//
//    private void foodFindingTest () {
//
//        System.out.println("========================================================================\n" +
//            "Running FoodServiceTestEngine.foodFindingTest...");
//
//        Food food = creationService.createFood();
//
//
////        System.out.println("FoodServiceTestEngine.foodFindingTest 1: Finding by null Id:");
////        Food searchResult = foodService.findById(null);
////        String testResult1 =
////            searchResult == null ? "SUCCESS" : "FAILURE";
////        System.out.println("FoodServiceTestEngine.foodFindingTest Result 1:" + testResult1);
////
////        System.out.println("FoodServiceTestEngine.foodFindingTest 2: Finding by null name:");
////        Food searchResult = foodService.findByName("");
////        String testResult2 = searchResult == null ? "SUCCESS" : "FAILURE";
////        System.out.println("FoodServiceTestEngine.foodFindingTest Result 1:" + testResult2);
////
//        Long id = randomSelectionService.randomFood().getId();
//        System.out.println("FoodServiceTestEngine.foodFindingTest 3: Finding by id that exists: " + id);
//        Food searchResult = foodService.findById(id);
//        String testResult1 = searchResult != null && searchResult.getId().equals(id) ? "SUCCESS" : "FAILURE";
//        System.out.println("FoodServiceTestEngine.foodFindingTest Result 3:" + testResult1);
//
//        id = foodService.count() + 2;
//        System.out.println("FoodServiceTestEngine.foodFindingTest 3: Finding by outside range: " + id);
//        searchResult = foodService.findById(id);
//        String testResult4 = searchResult == null ? "SUCCESS" : "FAILURE";
//        System.out.println("FoodServiceTestEngine.foodFindingTest Result 4:" + testResult4);
//
//
//        String name = randomSelectionService.randomFood().getName();
//        System.out.println("FoodServiceTestEngine.foodFindingTest 5: Finding by name that exists: " + name);
//        searchResult = foodService.findByName(name);
//        String testResult5 = searchResult != null ? "SUCCESS" : "FAILURE";
//        System.out.println("FoodServiceTestEngine.foodFindingTest Result 3:" + testResult5);
//
//        System.out.println("========================================================================\n" +
//            "Completed FoodServiceTestEngine.foodSavingTest...");
//    }
//
//    private void testDeletingFood() {
//        Food food = randomSelectionService.randomFood();
//        Long id = food.getId();
//        foodService.deleteById(id);
//        System.out.println("Deleted food with id:" + id);
//        System.out.println("Deleted food details:" + foodService.findById(id));
//    }
//
//    private void testUpdatingFood() {
//        Food food = randomSelectionService.randomFood();
//        Long id = food.getId();
//        String oldName = food.getName();
//
//        foodService.update(food.getId(), new Food(null, randomPropertyService.foodName()));
//        System.out.println("Updated food with id:" + id + " old name:" + oldName + " new name:" + food.getName());
//        System.out.println("Updated food details:" + foodService.findById(id));
//    }
//
////    private void testFoodService() {
////        System.out.println("Testing recipeMealOrchestrator.FoodService...");
////        Food food;
////        // Example: Create Food via Controller
////        for (int i = 0; i < NUMBER_OF_FOODS; i++) {
////            String name = FoodNameDataset.name();
////            while (foodService.findByName(name) != null) {
////                name = FoodNameDataset.name();
////            }
////            food = recipeMealOrchestratorService.saveFood(new Food(null, name));
////            System.out.println("Created food via orchestrator:" + food);
////        }
////        food = recipeMealOrchestratorService.getRandomFood();
////        Long foodId = food.getId();
////
////        System.out.println("Retrieved food via orchestrator:" + recipeMealOrchestratorService.findFoodById(foodId));
////
////        System.out.println(
////            "recipeMealOrchestrator deleted food with id:" + foodId
////                + " details:" + recipeMealOrchestratorService.findFoodById(foodId)
////        );
////        recipeMealOrchestratorService.deleteFood(foodId);
////
////        food = recipeMealOrchestratorService.getRandomFood();
////        foodId = food.getId();
////        String oldFoodName = food.getName();
//////        recipeMealOrchestratorService.updateFood(foodId, new Food(null, FoodNameDataset.name()));
////        System.out.println("recipeMealOrchestrator updated food with id old name:" + oldFoodName);
////        System.out.println(" updated food details:" + recipeMealOrchestratorService.findFoodById(foodId));
////
////        String searchString = "be";
////        System.out.println("Finding foods with name containing '" + searchString + "'");
////        recipeMealOrchestratorService.searchFoods(searchString).forEach(System.out::println);
////
////        food = recipeMealOrchestratorService.getRandomFood();
////        Long deletedFoodId = food.getId();
////        System.out.println("Deleting food with id:" + food.getId() + " details:" + food);
////        recipeMealOrchestratorService.deleteFood(food.getId());
////        food = recipeMealOrchestratorService.findFoodById(deletedFoodId);
////        if (food != null) {
////            System.out.println("Food with id:" + deletedFoodId + " still exists!");
////        } else {
////            System.out.println("Food with id:" + deletedFoodId + " has been deleted!");
////        }
////
////    }
////
////    public void testRecipeService() {
////        System.out.println("Testing recipeMealOrchestrator.RecipeService...");
////        Recipe recipe;
////        for (int i = 0; i < NUMBER_OF_RECIPES; i++) {
////            recipe = recipeMealOrchestratorService.saveRecipe(new Recipe(null, RecipeTitleDataset.title(), RecipeDescriptionDataset.description()));
////            addSteps(recipe);
////            System.out.println("Created recipe via orchestrator:" + recipeMealOrchestratorService.findRecipeById(recipe.getId()));
////        }
////
////        System.out.println("All Recipes via Orchestrator ServiceFacade:");
////        recipeMealOrchestratorService.findAllRecipes().forEach(System.out::println);
////
////        recipe = recipeMealOrchestratorService.getRandomRecipe();
////        System.out.println("Deleting recipe with id:" + recipe.getId());
////        recipeMealOrchestratorService.deleteRecipe(recipe.getId());
////
////        recipe = recipeMealOrchestratorService.getRandomRecipe();
////        Long recipeId = recipe.getId();
////        String oldTitle = recipe.getTitle();
////        String oldDescription = recipe.getDescription();
////        recipeMealOrchestratorService.updateRecipe(recipeId, new Recipe(null, RecipeTitleDataset.title(), RecipeDescriptionDataset.description()));
////        System.out.println("Updated Recipe old title:" + oldTitle + " old description:" + oldDescription);
////        System.out.println("updated recipe current info:" + recipeMealOrchestratorService.findRecipeById(recipeId).toString());
////
////        String searchString = "butter";
////        System.out.println("Finding recipes whose titles or descriptions contain the word '" + searchString + "'");
////        recipeMealOrchestratorService.searchRecipes(searchString).forEach(System.out::println);
////    }
////
////    private void addSteps(Recipe recipe) {
////        for (int i = 0; i < new Random().nextInt(2, 12); i++) {
////            Food food = recipeMealOrchestratorService.getRandomFood();
////            Double ingredientAmount = new Random().nextLong(0, 11) + 0.5;
////            String directions = StepStatementDataset.statement();
//////            System.out.println("\tSelected food:"  + food.getName() + " with ingredient amount: " + ingredientAmount);
//////            System.out.println("\tSelected directions: " + directions);
////            Step step = new Step(null, recipe, food, directions);
//////            food.addStep(step);
//////            recipe.addStep(step);
////            recipeMealOrchestratorService.saveStep(step);
////            recipeMealOrchestratorService.updateFood(food.getId(), food);
////            recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);
////
////            System.out.println("\tAdding step to recipe:" + step + " ingredient:" + step.getIngredient().getName() + " recipe:" + step.getRecipe().getTitle());
////        }
////        recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);
////        System.out.println("\tLeaving addSteps...");
////    }
//}