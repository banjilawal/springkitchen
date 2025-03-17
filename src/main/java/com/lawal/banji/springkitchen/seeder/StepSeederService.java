package com.lawal.banji.springkitchen.seeder;

import com.lawal.banji.springkitchen.dataset.StepStatementDataset;
import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.food.service.FoodService;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import com.lawal.banji.springkitchen.recipe.service.RecipeService;
import com.lawal.banji.springkitchen.step.service.StepService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StepSeederService {

    private static Random random = new Random();

    private final RecipeService recipeService;
    private final FoodService foodService;
    private RecipeSeederService recipeSeederService;
    private IngredientSeederService ingredientSeederService;
    private StepService stepService;

    @Autowired
    public StepSeederService(RecipeSeederService recipeSeederService, IngredientSeederService ingredientSeederService, StepService stepService, RecipeService recipeService, FoodService foodService) {
        this.recipeSeederService = recipeSeederService;
        this.ingredientSeederService = ingredientSeederService;
        this.stepService = stepService;
        this.recipeService = recipeService;
        this.foodService = foodService;
    }

    @Transactional
    public void seed (int minSteps, int maxSteps) {
        if (maxSteps < 1 && minSteps <= maxSteps)
            throw new IllegalArgumentException("maxSteps must be greater than 0 and minSteps must be less than or equal to maxSteps");
        int numberOfSteps = random.nextInt(maxSteps - minSteps + 1) + minSteps;
        Food food;
        String directions = "";
        Double ingredientAmount;
        for (Recipe recipe : recipeService.findAll()) {
            for (int i = 0; i < numberOfSteps; i++) {
                directions = directions(recipe);
                Long durationMinutes = getMinutes(timeStringExtractor(directions));
                food = ingredient(recipe);
                ingredientAmount = ingredientAmount();
                Step step = new Step(null, recipe, food, directions );
                stepService.save(step);
                foodService.save(food);
                recipeService.save(recipe);
            }
        }
    }

    private Double ingredientAmount () { return random.nextDouble(11.0);}

    private Food ingredient (Recipe recipe) {
        Food food = foodService.randomFood();
        while (recipe.filterStepsByIngredient(food) != null) foodService.randomFood();
        return food;
    }

    private String directions (Recipe recipe) {
        String directions = StepStatementDataset.instruction();
        while (recipe.filterStepsByDirections(directions) != null) directions = StepStatementDataset.instruction();
        return directions;
    }

    private Long randomMinutes () {
        long outcome = random.nextLong(100L);
        if (outcome < 70) return null;
        return random.nextLong(61L);
    }

    public String[] timeStringExtractor(String string) {
        String timeRegex = "\\b(\\d+)\\s*(seconds|minutes|hours|days|weeks)\\b";
        Pattern pattern = Pattern.compile(timeRegex);
        String[] timeList = new String[2];

        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            timeList[0] = matcher.group(1);
            timeList[1] = matcher.group(2);
        } else {
            timeList[0] = "10";
            timeList[1] = "minutes";
        }
        return timeList;
    }

    private Long getMinutes(String[] string) {
        Long duration = Long.parseLong(string[0]);
        if (string[1].equals("seconds")) return 1L;
        else if (string[1].equals("hours")) return duration * 60L ;
        else return duration;
    }
}