package com.lawal.banji.springkitchen.recipe.model;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.meal.model.Meal;
import com.lawal.banji.springkitchen.recipe.model.exception.RecipeDescriptionNullException;
import com.lawal.banji.springkitchen.recipe.model.exception.RecipeTitleNullException;
import com.lawal.banji.springkitchen.step.model.Step;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "recipes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = RecipeTitleNullException.MESSAGE)
    private String title;

    @Column(nullable = false)
    @NotBlank(message = RecipeDescriptionNullException.MESSAGE)
    private String description;

    /* Bidirectional fields */
    @OneToMany(mappedBy="recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Step> steps = new HashSet<>();

    @OneToMany(mappedBy="recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Meal> meals = new HashSet<>();

    public Recipe(Long id, String title, String description) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        this.steps = new HashSet<>();
        this.meals = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Set<Step> getSteps() { return steps;}

    public Set<Meal> getMeals() {
        return meals;
    }


    public void setId(Long id) { this.id = id; }

    public void setDescription(String description) {
        RecipeValidator.validateRecipeMethodStringParameter(description);
        this.description = description.trim();
    }

    public void setTitle(String title) {
        RecipeValidator.validateRecipeMethodStringParameter(title);
//        if (title == null || title.trim().isBlank()) loggingExceptionHandler(RECIPE_TITLE_CANNOT_BE_NULL);
        this.title = title.trim();
    }

    public void setSteps(Set<Step> steps) {
        RecipeValidator.validateRecipeMethodStepSetParameter(steps);
        if (this.steps == null) this.steps = new HashSet<>();
        this.steps.clear();
        for (Step step : new HashSet<>(steps)) {
            addStep(step);
        }
    }

    public void addStep(Step step) {
        RecipeValidator.validateRecipeMethodStepParameter(step);
        if (this.steps == null) this.steps = new HashSet<>();
//
//        for (Step s : steps) {
//            System.out.println("recipe has step " + s);
//        }

        if (step == null) return;
        step.setRecipe(this);
        steps.add(step);
    }

    public void removeStep(Step step) {
        RecipeValidator.validateRecipeMethodStepParameter(step);
        if (this.steps == null) this.steps = new HashSet<>();
        if (steps.contains(step)) {
            this.steps.remove(step);
            if (step.getRecipe() == this) step.setRecipe(null);
        }
    }

    // Meal mutator methods
    public void setMeals (Set<Meal> meals) {
        RecipeValidator.validateRecipeMethodMealSetParameter(meals);
        if (this.meals == null) this.meals = new HashSet<>();
        for (Meal meal : new HashSet<>(meals)) {
            addMeal(meal);
        }
    }

    public void addMeal (Meal meal) {
//        RecipeValidator.validateRecipeMethodMealParameter(meal);
        if (this.meals == null) this.meals = new HashSet<>();
        if (meal == null) return;

        meal.setRecipe(this);
        meals.add(meal);
    }

    public void removeMeal (Meal meal) {
        RecipeValidator.validateRecipeMethodMealParameter(meal);
        if (this.meals == null) this.meals = new HashSet<>();
        if (meals.contains(meal)) {
            this.meals.remove(meal);
            meal.setRecipe(null);
        }
    }

    /* Search methods */
    // find a unique step
    public Step findStepById(Long id) {
        RecipeValidator.validateRecipeMethodLongParameter(id);
        if (steps == null) {
            steps = new HashSet<>();
            return null;
        }
        for (Step step : steps) {
            if (step.getId().equals(id)) return step;
        }
        return null;
    }

    // Step filtration methods
    public Set<Step> filterStepsByDirections(String string) {
        Set<Step> matches = new HashSet<>();
        if (steps == null) steps = new HashSet<>();
        if (string != null && !string.isBlank()) {
            string = string.toLowerCase();
            for (Step step : steps) {
                if (step.getInstruction().toLowerCase().contains(string)
                    || step.getIngredient().getName().toLowerCase().contains(string)
                ) matches.add(step);
            }
        }
        return matches;
    }

    public Set<Step> filterStepsByIngredient(Food food) {
        RecipeValidator.validateRecipeMethodFoodParameter(food);
        Set<Step> matches = new HashSet<>();
        if (steps == null) steps = new HashSet<>();
        for (Step step : new HashSet<>(steps)) {
            if (step.getIngredient().equals(food)) matches.add(step);
        }
        return matches;
    }

    // Find a unique meal
    public Meal findMealById(Long id) {
        RecipeValidator.validateRecipeMethodLongParameter(id);
        if (meals == null) meals = new HashSet<>();
        for (Meal meal : new HashSet<>(meals)) {
            if (meal.getId().equals(id)) return meal;
        }
        return null;
    }

    // Meal filtration methods
    public Set<Meal> filterMealsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        RecipeValidator.validateFilterMealsByDateRangeParameter(startDate, endDate);
        HashSet<Meal> matches = new HashSet<>();
        if (meals == null) meals = new HashSet<>();
        for (Meal meal : new HashSet<>(meals)) {
            if (meal.isInDateRange(startDate, endDate)) matches.add(meal);
        }
        return matches;
    }

    // Get recipe's ingredients
    public Set<Food> getIngredients() {
        Set<Food> foods = new HashSet<>();
        if (steps == null) steps = new HashSet<>();
        for (Step step : steps) {
            foods.add(step.getIngredient());
        }
        return foods;
    }

    /* Update methods */
    public void getUpdate(Recipe source) {
        if (source == this) return;
        RecipeValidator.validateRecipeUpdateSource(source);
//        RecipeValidator.validateIdMatch(this, source);
            setTitle(source.getTitle());
            setDescription(source.getDescription());
            setSteps(source.getSteps());
            setMeals(source.getMeals());
    }

    /* String methods */
    @Override
    public String toString() {
        if (this == null) return "null recipe object.";
        String idString = id == null ? "" : id + "";

        if (steps == null) steps = new HashSet<>();
        if (meals == null) meals = new HashSet<>();

        return getClass().getSimpleName()
            + "[recipeId:" + id
            + " title:" + title
            + " description:" + description
            + " total steps:" + steps.size()
            + " total meals:" + meals.size() + ']';
    }

    public String stepsToString() {
        StringBuilder stringBuilder = new StringBuilder("Steps:");
        if (steps == null) {
            steps = new HashSet<>();
            return "steps:0";
        } else {
            int counter = 1;
            for (Step step : steps) {
                stringBuilder.append(counter).append(" ").append(step.toString()).append("\n");
                counter++;
            }
        }
        return stringBuilder.toString();
    }

    public String mealsToString() {
        StringBuilder stringBuilder = new StringBuilder("Meals:\n");
        if (meals == null) {
            meals = new HashSet<>();
            return "meals:0";
        } else {
            int counter = 1;
            for (Meal meal : meals) {
                stringBuilder.append(counter).append(" ").append(meal.toString()).append("\n");
                counter++;
            }
        }
        return stringBuilder.toString();
    }

    public Step randomStep() {
        if (steps == null) {
            steps = new HashSet<>();
            return null;
        }
        if (steps.isEmpty()) return null;

        List<Step> list = new ArrayList<>(steps);
        return list.get(new Random().nextInt(list.size()));
    }
}