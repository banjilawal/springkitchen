package com.lawal.banji.springkitchen.recipe.model;

import com.lawal.banji.springkitchen.food.Food;
import com.lawal.banji.springkitchen.meal.Meal;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "recipes")
public class Recipe {

    /* String constants */
    public static final String RECIPE_UPDATE_SOURCE_CANNOT_BE_NULL = "Recipe update Source cannot be null";
    public static final String INVALID_UPDATE_SOURCE_ID = "Recipe update source id is not ivalid. Update failed";

    public static final String RECIPE_TITLE_CANNOT_BE_NULL = "Recipe title cannot be null or blank";
    public static final String RECIPE_DESCRIPTION_CANNOT_BE_NULL = "Recipe description cannot be null or blank";

    private static final String METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS = "Recipe method does not allow null id parameters";
    public static final String NULL_STEPS_COLLECTION_NOT_ALLOWED = "Recipe method doe not allow null step collections";
    public static final String NULL_MEALS_COLLECTION_NOT_ALLOWED = "Recipe method does not allow null meal collections";

    public static final String NULL_STEP_CANNOT_BE_PASSED_TO_RECIPE_METHOD = "Recipe method does not allow a null step";
    public static final String NULL_MEAL_CANNOT_BE_PASSED_TO_RECIPE_METHOD = "Recipe method does not allow a null  meal";

    /* logger */
    private static final Logger logger = LoggerFactory.getLogger(Recipe.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = RECIPE_TITLE_CANNOT_BE_NULL)
    private String title;

    @Column(nullable = false)
    @NotBlank(message = RECIPE_DESCRIPTION_CANNOT_BE_NULL)
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
        if (title == null || title.trim().isBlank()) loggingExceptionHandler(RECIPE_TITLE_CANNOT_BE_NULL);
        else { this.title = title.trim(); }
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isBlank()) loggingExceptionHandler(RECIPE_DESCRIPTION_CANNOT_BE_NULL);
        else { this.description = description.trim(); }
    }

    // Step mutator methods
    public void setSteps(Set<Step> steps) {
        if (steps == null) loggingExceptionHandler(NULL_STEPS_COLLECTION_NOT_ALLOWED);
        else {
            if (this.steps == null) this.steps = new HashSet<>();
            this.steps.clear();
            for (Step step : new HashSet<>(steps)) {
                addStep(step);
            }
        }
    }

    public void addStep(Step step) {
        if (step == null) loggingExceptionHandler(NULL_STEPS_COLLECTION_NOT_ALLOWED);
        else {
            if (this.steps == null) this.steps = new HashSet<>();
            if (!steps.contains(step)) {
                steps.add(step);
                step.setRecipe(this);
            }
        }
    }

    public void removeStep(Step step) {
        if (step == null) loggingExceptionHandler(NULL_STEP_CANNOT_BE_PASSED_TO_RECIPE_METHOD);
        else {
            if (this.steps == null) this.steps = new HashSet<>();
            if (steps.contains(step)) {
                this.steps.remove(step);
                if (step.getRecipe() == this) step.setRecipe(null);
            }
        }
    }

    // Meal mutator methods
    public void setMeals (Set<Meal> meals) {
        if (meals == null) loggingExceptionHandler(NULL_MEALS_COLLECTION_NOT_ALLOWED);
        else {
            if (this.meals == null) this.meals = new HashSet<>();
            for (Meal meal : new HashSet<>(meals)) {
                addMeal(meal);
            }
        }
    }

    public void addMeal (Meal meal) {
        if (meal == null) loggingExceptionHandler(NULL_MEAL_CANNOT_BE_PASSED_TO_RECIPE_METHOD);
        else {
            if (this.meals == null) this.meals = new HashSet<>();
            if (!meals.contains(meal)) {
                meals.add(meal);
                meal.setRecipe(this);
            }
        }
    }

    public void removeMeal (Meal meal) {
        if (meal == null) loggingExceptionHandler(NULL_MEAL_CANNOT_BE_PASSED_TO_RECIPE_METHOD);
        else {
            if (this.meals == null) this.meals = new HashSet<>();
            if (meals.contains(meal)) {
                this.meals.remove(meal);
                meal.setRecipe(null);
            }
        }
    }

    /* Search methods */
    // find a unique step
    public Step findStepById(Long id) {
        if (id == null) loggingExceptionHandler(METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS);
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
        if (string != null && string.trim().isEmpty()) {
            for (Step step : steps) {
                if (step.getDirections().toLowerCase().contains(string.toLowerCase())
                        | step.getIngredient().getName().toLowerCase().contains(string)) matches.add(step);
            }
        }

        return matches;
    }

    public Set<Step> filterStepsByIngredient(Food food) {
        Set<Step> matches = new HashSet<>();
        if (steps == null) steps = new HashSet<>();
        if (food != null) {
            for (Step step : new HashSet<>(steps)) {
                if (step.getIngredient().equals(food)) matches.add(step);
            }
        }
        return matches;
    }

    // Find a unique meal
    public Meal findMealById(Long id) {
        if (id == null) loggingExceptionHandler(METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS);
        if (meals == null) meals = new HashSet<>();
        for (Meal meal : new HashSet<>(meals)) {
            if (meal.getId().equals(id)) return meal;
        }
        return null;
    }

    // Meal filtration methods
    public Set<Meal> filterMealsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        HashSet<Meal> matches = new HashSet<>();
        if (startDate != null && endDate != null) {
            if (meals == null) meals = new HashSet<>();
            for (Meal meal : new HashSet<>(meals)) {
                if (meal.isInDateRange(startDate, endDate)) matches.add(meal);
            }
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

        if (source == null) loggingExceptionHandler(RECIPE_UPDATE_SOURCE_CANNOT_BE_NULL);
        else if (source.getSteps() == null) loggingExceptionHandler(NULL_STEPS_COLLECTION_NOT_ALLOWED);
        else if (source.getMeals() == null) loggingExceptionHandler(NULL_MEALS_COLLECTION_NOT_ALLOWED);
        else if (source.getId() == null || !id.equals(source.getId())) loggingExceptionHandler(INVALID_UPDATE_SOURCE_ID);
        else if (source.getTitle() == null || source.getTitle().trim().isBlank()) loggingExceptionHandler(RECIPE_TITLE_CANNOT_BE_NULL);
        else if (source.getDescription() == null || source.getDescription().trim().isBlank()) loggingExceptionHandler(RECIPE_DESCRIPTION_CANNOT_BE_NULL);
        else {
            setTitle(source.getTitle());
            setDescription(source.getDescription());
            setSteps(source.getStepsAsSet());
            setMeals(source.getMealsAsSet());
        }
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

    /* logging methods */
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}