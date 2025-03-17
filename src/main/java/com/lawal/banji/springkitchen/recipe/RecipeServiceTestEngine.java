//package com.lawal.banji.springkitchen.recipe;
//
//import com.lawal.banji.springkitchen.food.model.Food;
//import com.lawal.banji.springkitchen.food.service.FoodService;
//import com.lawal.banji.springkitchen.common.RandomPropertyService;
//
//import com.lawal.banji.springkitchen.common.RandomSelectionService;
//import com.lawal.banji.springkitchen.recipe.model.Recipe;
//import com.lawal.banji.springkitchen.recipe.service.RecipeService;
//import com.lawal.banji.springkitchen.seeder.IngredientSeederService;
//import com.lawal.banji.springkitchen.step.model.Step;
//import com.lawal.banji.springkitchen.step.service.StepService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.Random;
//
//@Component
//@Order(2)
//public class RecipeServiceTestEngine implements CommandLineRunner {
//
//    public static final int NUMBER_OF_RECIPES = 20;
//    public static final int MIN_NUMBER_OF_STEPS = 3;
//    public static final int MAX_NUMBER_OF_STEPS = 11;
//    public static final int MAX_FOOD_OCCURRENCES = 2;
//
//    @Autowired
//    private RecipeService recipeService;
//
//    @Autowired
//    private StepService stepService;
//
//    @Autowired
//    private RandomPropertyService randomPropertyService;
//
//    @Autowired
//    RandomSelectionService randomSelectionService;
//
//    @Autowired
//    private FoodService foodService;
//
//    @Autowired
//    private IngredientSeederService ingredientSeederService;
//
//    @Autowired
//    private OrchestrationService orchestrationService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Starting RecipeServiceTestEngine...");
//
//        System.out.println("Starting RecipeService.save Tests......");
//        testSavingRecipe();
//        System.out.println("Finished RecipeService.save Tests......");
//
//        System.out.println("Starting RecipeService.find Tests......");
//        testFindingRecipe();
//        System.out.println("Finished RecipeService.find Tests......");
//
//        System.out.println("Starting RecipeService.update Tests......");
//        testUpdatingRecipe();
//        System.out.println("Finished RecipeService.update Tests......");
//
//        System.out.println("Starting RecipeService.delete Tests......");
//        testDeletingRecipe();
//        System.out.println("Finished RecipeService.delete Tests......");
//
//        System.out.println("Starting RecipeService.addStep Tests......");
//        testAddingRecipeSteps();
//        System.out.println("Finished RecipeService.addStep Tests......");
//
//        System.out.println("Starting RecipeService.updateStep Tests......");
//        testAddingRecipeSteps();
//        System.out.println("Finished RecipeService.updateStep Tests......");
//
//        System.out.println("Starting RecipeService.deleteStep Tests......");
//        testDeletingStep();
//        System.out.println("Finished RecipeService.deleteStep Tests......");
//
//    }
//
//    private void testSavingRecipe() {
//        for (int i = 0; i < NUMBER_OF_RECIPES; i++) {
//            Recipe recipe = new Recipe(
//                null,
//                randomPropertyService.recipeTitle(),
//                randomPropertyService.recipeDescription()
//            );
//            recipeService.save(recipe);
//            System.out.println("Saved recipe:" + recipe);
//        }
//    }
//
//    private void testFindingRecipe() {
//        Long randomId = recipeService.count() - 1;
//        Recipe recipe = recipeService.findById(randomId);
//        System.out.println("Found recipe:" + recipe);
//    }
//
//    private void testDeletingRecipe() {
//        Recipe recipe = randomSelectionService.randomRecipe();
//        Long id = recipe.getId();
//        recipeService.deleteById(id);
//        System.out.println("Deleted recipe with id:" + id);
//        System.out.println("Deleted recipe details:" + recipeService.findById(id));
//    }
//
//    private void testUpdatingRecipe() {
//        Recipe recipe = randomSelectionService.randomRecipe();
//
//        Long id = recipe.getId();
//        String oldTitle = recipe.getTitle();
//        recipe.setTitle(randomPropertyService.recipeTitle());
//
//        recipeService.update(id, recipe);
//        System.out.println("Updated recipe with id:" + id
//            + " old title:" + oldTitle
//            + " current title:" + recipe.getTitle()
//        );
//        System.out.println("Updated recipe details:" + recipeService.findById(id));
//    }
//
//    private void testAddingRecipeSteps() {
//        for (Recipe recipe : recipeService.findAll()) {
//            addStepsHelper(recipe);
//            recipeService.update(recipe.getId(), recipe);
//            System.out.println("Updated recipe:" + recipe);
//            System.out.println("Updated recipe steps:" + recipe.stepsToString());
//        }
//
//    }
//
//    private void addStepsHelper(Recipe recipe) {
//        int numberOfSteps = new Random().nextInt(MIN_NUMBER_OF_STEPS, MAX_NUMBER_OF_STEPS);
//        System.out.println(numberOfSteps);
//        for (int i = 1; i < numberOfSteps; i++) {
//            Food ingredient = randomSelectionService.randomFood() ;
//
//            while (recipe.getIngredients().contains(ingredient)) {
//                ingredient = randomSelectionService.randomFood();
//            }
//
//            String directions = randomPropertyService.stepInstruction();
//            while (!recipe.filterStepsByDirections(directions).isEmpty()) {
//                directions = randomPropertyService.stepInstruction();
//            }
//
//            Step step = new Step(null, recipe, ingredient, directions);
//            stepService.save(step);
////            System.out.println(step);
////            System.out.println("step.RecipeId:" + step.getRecipe().getId() + "\nstep.IngredientId:" + step.getIngredient().getId());
////            recipe.addStep(step);
////            recipeService.update(recipe.getId(), recipe);
////
////            ingredient.addStep(step);
////            foodService.update(ingredient.getId(), ingredient);
//            step.setIngredient(randomSelectionService.randomFood());
////            System.out.println(ingredient + " usages:" + ingredient.getSteps().size());
////            System.out.println(recipe.getId() + " steps:" + recipe.getSteps().size());
////
////            System.out.println("Ingredient:" + ingredient);
//        }
//    }
//
//    private void testUpdatingRecipeSteps() {
//
//        Recipe recipe = randomSelectionService.randomRecipe();
//        Step step = recipe.randomStep();
//
//        System.out.println("Initial Recipe State:" + recipe + "\n" + recipe.stepsToString());
//        System.out.println("random step:" + step);
//
//        step.setDirections(randomPropertyService.stepInstruction());
//        stepService.save(step);
//
//        System.out.println("Updated Recipe State:" + recipe + " " + recipe.stepsToString());
//        System.out.println("Updated step:" + step);
//
//        Food ingredient = step.getIngredient();
//        System.out.println(step.getIngredient() + "\n" + ingredient.stepsToString());
//        step.setIngredient(randomSelectionService.randomFood());
//
//        stepService.save(step);
//
//        ingredient = step.getIngredient();
//        System.out.println(step.getIngredient() + "\n" + ingredient.stepsToString());
//    }
//
//    public void testDeletingStep() {
//        Recipe recipe = randomSelectionService.randomRecipe();
//        Step step = recipe.randomStep();
//        Food food = step.getIngredient();
//
//        int counter = 0;
//        System.out.println("Recipe:" + recipe.getTitle());
//        for (Step s : recipe.getSteps()) {
//            Food ingredient = s.getIngredient();
//            System.out.print(counter + " stepId:" + s.getId()
//                + " foodId:" + ingredient.getId()
//                + " food.usages:" + ingredient.getSteps().size() + "\n"
//            );
//            counter++;
//        }
//
//        Long stepId = step.getId();
//        orchestrationService.deleteStep(stepId);
//
//        System.out.println("Deleted step with id:" + stepId);
//        Step searchResult = stepService.findById(stepId);
//
//        recipe.removeStep(step);
//        food.removeStep(step);
//
//        recipeService.update(recipe.getId(), recipe);
//        foodService.update(food.getId(), food);
//
////        System.out.println("Deletion post search result on " + stepId + ": " + searchResult);
//        counter = 0;
//        System.out.println("Recipe:" + recipe.getTitle());
//        for (Step s : recipe.getSteps()) {
//            Food ingredient = s.getIngredient();
//            System.out.print(counter + " stepId:" + s.getId()
//                + " foodId:" + ingredient.getId()
//                + " food.usages:" + ingredient.getSteps().size() + "\n"
//            );
//            counter++;
//        }
//    }
//
////    private void testRecipeService() {
////        System.out.println("Testing recipeMealOrchestrator.RecipeService...");
////        Recipe recipe;
////        // Example: Create Recipe via Controller
////        for (int i = 0; i < NUMBER_OF_RECIPES; i++) {
////            String name = RecipeNameDataset.name();
////            while (recipeService.findByName(name) != null) {
////                name = RecipeNameDataset.name();
////            }
////            recipe = recipeMealOrchestratorService.saveRecipe(new Recipe(null, name));
////            System.out.println("Created recipe via orchestrator:" + recipe);
////        }
////        recipe = recipeMealOrchestratorService.getRandomRecipe();
////        Long recipeId = recipe.getId();
////
////        System.out.println("Retrieved recipe via orchestrator:" + recipeMealOrchestratorService.findRecipeById(recipeId));
////
////        System.out.println(
////            "recipeMealOrchestrator deleted recipe with id:" + recipeId
////                + " details:" + recipeMealOrchestratorService.findRecipeById(recipeId)
////        );
////        recipeMealOrchestratorService.deleteRecipe(recipeId);
////
////        recipe = recipeMealOrchestratorService.getRandomRecipe();
////        recipeId = recipe.getId();
////        String oldRecipeName = recipe.getName();
//////        recipeMealOrchestratorService.updateRecipe(recipeId, new Recipe(null, RecipeNameDataset.name()));
////        System.out.println("recipeMealOrchestrator updated recipe with id old name:" + oldRecipeName);
////        System.out.println(" updated recipe details:" + recipeMealOrchestratorService.findRecipeById(recipeId));
////
////        String searchString = "be";
////        System.out.println("Finding recipes with name containing '" + searchString + "'");
////        recipeMealOrchestratorService.searchRecipes(searchString).forEach(System.out::println);
////
////        recipe = recipeMealOrchestratorService.getRandomRecipe();
////        Long deletedRecipeId = recipe.getId();
////        System.out.println("Deleting recipe with id:" + recipe.getId() + " details:" + recipe);
////        recipeMealOrchestratorService.deleteRecipe(recipe.getId());
////        recipe = recipeMealOrchestratorService.findRecipeById(deletedRecipeId);
////        if (recipe != null) {
////            System.out.println("Recipe with id:" + deletedRecipeId + " still exists!");
////        } else {
////            System.out.println("Recipe with id:" + deletedRecipeId + " has been deleted!");
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
////            Recipe recipe = recipeMealOrchestratorService.getRandomRecipe();
////            Double ingredientAmount = new Random().nextLong(0, 11) + 0.5;
////            String directions = StepStatementDataset.statement();
//////            System.out.println("\tSelected recipe:"  + recipe.getName() + " with ingredient amount: " + ingredientAmount);
//////            System.out.println("\tSelected directions: " + directions);
////            Step step = new Step(null, recipe, recipe, directions);
//////            recipe.addStep(step);
//////            recipe.addStep(step);
////            recipeMealOrchestratorService.saveStep(step);
////            recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);
////            recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);
////
////            System.out.println("\tAdding step to recipe:" + step + " ingredient:" + step.getIngredient().getName() + " recipe:" + step.getRecipe().getTitle());
////        }
////        recipeMealOrchestratorService.updateRecipe(recipe.getId(), recipe);
////        System.out.println("\tLeaving addSteps...");
////    }
//}