package com.lawal.banji.springkitchen.recipe.model;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.recipe.model.exception.RecipeDescriptionNullException;
import com.lawal.banji.springkitchen.recipe.model.exception.RecipeTitleNullException;
import com.lawal.banji.springkitchen.step.model.Step;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
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

    /* Constructors */
    public Recipe() {}

    public Recipe(Long id, String title, String description) {
        this.id = id;
        setTitle(title);
        setDescription(description);
        this.steps = new HashSet<>();
        this.meals = new HashSet<>();
    }

    /* Builders */
    public static Builder builder () { return new Builder(); }

    public static class Builder {
        private Long id;
        private String title;
        private String description;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Recipe build() { return new Recipe(id, title, description); }
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Step> getSteps() {
        return getStepsAsList();
    }

    public List<Meal> getMeals() {
        return getMealsAsList();
    }

    public List<Meal> getMealsAsList() {
        return new ArrayList<>(meals);
    }

    public Set<Meal> getMealsAsSet () {
        return new HashSet<>(meals);
    }

    public List<Step> getStepsAsList() {
        return new ArrayList<>(steps);
    }

    public Set<Step> getStepsAsSet() {
        return new HashSet<>(steps);
    }

    /* Setters */
    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) {
        RecipeValidator.validateRecipeMethodStringParameter(title);
//        if (title == null || title.trim().isBlank()) loggingExceptionHandler(RECIPE_TITLE_CANNOT_BE_NULL);
        this.title = title.trim();
    }

    public void setDescription(String description) {
        RecipeValidator.validateRecipeMethodStringParameter(description);
//        if (description == null || description.trim().isBlank()) loggingExceptionHandler(RECIPE_DESCRIPTION_CANNOT_BE_NULL);
        this.description = description.trim();
    }

    // Step mutator methods
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
        if (!steps.contains(step)) {
            steps.add(step);
            step.setRecipe(this);
        }
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
        RecipeValidator.validateRecipeMethodMealParameter(meal);
        if (this.meals == null) this.meals = new HashSet<>();
        if (!meals.contains(meal)) {
            meals.add(meal);
            meal.setRecipe(this);
        }
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
        if (steps == null) steps = new HashSet<>();
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
                if (step.getDirections().toLowerCase().contains(string)
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
            setSteps(source.getStepsAsSet());
            setMeals(source.getMealsAsSet());
    }

    /* Equals and hash methods */
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (object instanceof Recipe recipe ) {
            boolean equalIds = id != null && recipe.getId() != null && id.equals(recipe.getId());
            boolean equalTitles = title != null && recipe.getTitle() != null && title.equalsIgnoreCase(recipe.getTitle());
            boolean equalDescriptions = description != null && recipe.getDescription() != null
                && description.equalsIgnoreCase(recipe.getDescription());
            return equalIds && equalTitles && equalDescriptions;
        }
        return false;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    /* String methods */
    @Override
    public String toString() {
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
        StringBuilder stringBuilder = new StringBuilder("Steps:\n");
        if (steps == null) steps = new HashSet<>();
        else {
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
        if (meals == null) meals = new HashSet<>();
        else {
            int counter = 1;
            for (Meal meal : meals) {
                stringBuilder.append(counter).append(" ").append(meal.toString()).append("\n");
                counter++;
            }
        }
        return stringBuilder.toString();
    }
}