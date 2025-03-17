package com.lawal.banji.springkitchen.common;

import com.lawal.banji.springkitchen.common.orchestration.OrchestrationService;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Component
@Order(1)
public class SystemFactory implements CommandLineRunner {

    private int recipeRecurrenceCount = 0;
    private int foodRecurrenceCount = 0;

    @Autowired
    private CreationService creationService;

    @Autowired
    private OrchestrationService orchestrationService;

    @Autowired
    private RandomSelectionService randomSelectionService;


    @Override
    public void run (String... args) throws Exception {
        createFoods();
        createRecipes();
        createMealSchedule();
        System.out.println(buildReport());
        System.out.println("System Build Complete");
    }

    private void  createFoods() {

        System.out.println(heading( Constant.NUMBER_OF_FOODS + " foods"));

        for (int i = 0; i < Constant.NUMBER_OF_FOODS; i++) {
            System.out.println("\t\tCreated " + orchestrationService.saveFood(creationService.createFood()));
        }

        System.out.println(footer(Constant.NUMBER_OF_FOODS + " foods"));
//        System.out.println(orchestrationService.getFoodCount() + " foods successfully added to the system");
//        System.out.println("\n======================================================================================\n");
    }

    private void createRecipes() {

        System.out.println(heading(Constant.NUMBER_OR_RECIPES + " recipes"));
        for (int i = 0; i < Constant.NUMBER_OR_RECIPES; i++) {
            Recipe recipe = orchestrationService.saveRecipe(creationService.createRecipe());
            recipe = updateRecipeSteps(recipe);
            System.out.println("\t\tCreated " + recipe + " with " + recipe.getSteps().size() + " steps");
        }

        System.out.println(footer(Constant.NUMBER_OR_RECIPES + " recipes"));
//        System.out.println(orchestrationService.getRecipeCount() + " recipes successfully added to the system");
//        System.out.println("\n======================================================================================\n");
    }

    private  Recipe updateRecipeSteps(Recipe recipe) {

        int numberOfSteps = new Random().nextInt(Constant.MIN_RECIPE_STEPS, Constant.MAX_RECIPE_STEPS);
        System.out.println(recipe.getTitle() + " has " + numberOfSteps + " steps");

        for (int i = 0; i < numberOfSteps; i++) {
            Food food = randomSelectionService.randomFood();

            Step step = creationService.createStep(food, recipe);
            System.out.println("\t\t" + i + " Created step " + step);
            orchestrationService.saveStep(step);
        }
        return recipe;
//        return orchestrationService.findRecipeById(recipe.getId());
    }

    private void createMealSchedule() {

        int totalDays = Constant.NUMBER_OF_WEEKS_SCHEDULED * Constant.DAYS_PER_WEEK;
        System.out.println(heading(totalDays + " recipes"));

        for (int i = 0; i < totalDays ; i++) {
            createDayMeals(LocalDate.now().minusDays(i));
            createDayMeals(LocalDate.now().plusDays(i));
        }
        System.out.println(footer(Constant.NUMBER_OF_WEEKS_SCHEDULED + " recipes"));
    }

    private void createDayMeals(LocalDate day) {

        LocalDateTime servingTime = day.atStartOfDay().plusHours(8);
        LocalDateTime lastServingTime= day.atStartOfDay().plusHours(Constant.HOURS_BETWEEN_MEALS * 2);

//        Recipe previousRecipe = randomSelectionService.randomRecipe();
//        Meal meal = orchestrationService.saveMeal(new Meal(null, servingTime, previousRecipe));

        while (servingTime.isBefore(lastServingTime)) {
            System.out.println("current mealTime:" + servingTime);
            Meal meal = new Meal(null, servingTime, randomSelectionService.randomRecipe());
            System.out.println("\t\tCreated " + meal);
            orchestrationService.saveMeal(meal);
            servingTime = servingTime.plusHours(Constant.HOURS_BETWEEN_MEALS);
        }
    }

//    private Recipe recipeRecurrenceHandler(Recipe previousRecipe, Recipe currentRecipe) {
//
//        if (!currentRecipe.equals(previousRecipe)) return currentRecipe;
//        recipeRecurrenceCount++;
//
//        while (currentRecipe.equals(previousRecipe) || recipeRecurrenceCount > Constant.MAX_RECIPE__OCCURRENCES_PER_DAY) {
//            if(!currentRecipe.equals(previousRecipe)) recipeRecurrenceCount--;
//            else {
//                currentRecipe = randomSelectionService.randomRecipe();
//            }
//        }
//        return currentRecipe;
//    }

    private String buildReport() {
        String header = "======================================= System Build Report =======================================\n";
        String footer = "===================================================================================================\n";

        StringBuilder report = new StringBuilder().append(header)
            .append(foodSummaryReport())
            .append(recipeSummaryReport())
            .append(mealSummaryReport())
            .append(footer);
        return report.toString();
    }

    private String foodSummaryReport() {

        String header = "Foods\n";
        StringBuilder stringBuilder = new StringBuilder(header);

        for (Food food : orchestrationService.findAllFoods()) {
            stringBuilder.append(food.toString()).append("\n");
        }

        stringBuilder.append("Total Foods:").append(orchestrationService.getFoodCount()).append("\n");
        stringBuilder.append("----------------------------------------------------------------------------------------\n");

        return stringBuilder.toString();
    }

    private String recipeSummaryReport() {
        String header = "Recipes\n";
        StringBuilder stringBuilder = new StringBuilder(header);
        for (Recipe recipe : orchestrationService.findAllRecipes()) {
            stringBuilder.append(recipe.toString()).append("\n");
        }

        stringBuilder.append("Total Recipes:").append(orchestrationService.getRecipeCount()).append("\n");
        stringBuilder.append("----------------------------------------------------------------------------------------\n");

        return stringBuilder.toString();
    }

    private String mealSummaryReport() {
        String header = "Meals\n";
        StringBuilder stringBuilder = new StringBuilder(header);
        for (Meal meal : orchestrationService.findAllMeals()) {
            stringBuilder.append(meal.toString()).append("\n");
        }

        stringBuilder.append("Total Meals:").append(orchestrationService.getMealCount()).append("\n");
        stringBuilder.append("----------------------------------------------------------------------------------------\n");

        return stringBuilder.toString();
    }

    private String heading(String title) {
        return "Initializing the system with " + title
            + "\n======================================================================================\n";
    }

    private String footer(String title) {
        return title + " successfully added to the system\n"
            + "\n======================================================================================\n";
    }
}