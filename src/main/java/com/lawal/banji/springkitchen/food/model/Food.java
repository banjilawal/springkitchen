package com.lawal.banji.springkitchen.food.model;

import com.lawal.banji.springkitchen.food.model.exception.FoodNameNullException;
import com.lawal.banji.springkitchen.global.AppLogger;
import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


import java.util.*;

@Entity
@Table(name = "foods")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = FoodNameNullException.MESSAGE)
    String name;

    /* Bidirectional fields */
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Step> steps = new HashSet<>();

    /* Constructors */
    public Food() {}

    public Food(Long id, String name) { //}, Step step) {
        setId(id);
        setName(name);
        this.steps = new HashSet<>();
    }

    /* Getters */
    public Long getId() { return id; }

    public String getName() { return name; }

    public List<Step> getSteps() { return getStepsAsList(); }

    public List<Step> getStepsAsList() { return new ArrayList<>(steps); }

    public Set<Step> getStepsAsSet() { return new HashSet<>(steps); }

    /* Setters */
    public void setId(Long id) {
        AppLogger.debug(Food.class, "Setting id to " + id);
//        FoodValidator.validateFoodMethodLongParameter(id);
        this.id = id;
        AppLogger.info(Food.class, "Successfully set id to " + id);
    }

    public void setName (String name) {
        AppLogger.debug(Food.class, "Setting name to " + name);
        FoodValidator.validateFoodMethodStringParameter(name);
        this.name = name.trim();
        AppLogger.info(Food.class, "Successfully set name to " + name);
    }

    // Step mutator methods
    public void setSteps(Set<Step> steps) {
        AppLogger.debug(Food.class, "Setting steps");
        FoodValidator.validateFoodMethodStepSetParameter(steps);
        if (this.steps == null) this.steps = new HashSet<>();
        this.steps.clear();
        steps.forEach(this::addStep);
        AppLogger.info(Food.class, "Successfully set steps");
    }

    public void addStep(Step step) {
        AppLogger.debug(Food.class, "Adding step " + step.toString());
        FoodValidator.validateFoodMethodStepParameter(step);
        if (this.steps == null) this.steps = new HashSet<>();
        if (!steps.contains(step)) {
            steps.add(step);
            step.setIngredient(this);
        }
        AppLogger.info(Food.class, "Successfully added step " + step.toString());
    }

    public void removeStep(Step step) throws IllegalArgumentException {
        AppLogger.debug(Food.class, "Removing step " + step.toString());
        FoodValidator.validateFoodMethodStepParameter(step);
        if (this.steps == null) this.steps = new HashSet<>();
        if (steps.contains(step)) {
            this.steps.remove(step);
            if (step.getIngredient().equals(this)) step.setRecipe(null);
        }
        AppLogger.info(Food.class, "Successfully removed step " + step.toString());
    }

    /* Search methods */
    // find a unique step
    public Step findStepById(Long id) {
        AppLogger.debug(Food.class, "Finding step by id " + id);
        FoodValidator.validateFoodMethodLongParameter(id);
        if (steps == null) steps = new HashSet<>();
        for (Step step : steps) {
            if (step.getId().equals(id)) {
                AppLogger.info(Food.class, "Successfully found step by id " + id);
                return step;
            }
        }
        AppLogger.info(Food.class, "Failed to find step by id " + id);
        return null;
    }

    // Step filtration methods
    public Set<Step> filterStepsByDirections(String string) {
        Set<Step> matches = new HashSet<>();
        AppLogger.debug(Food.class, "Filtering steps by directions " + string);
        if (steps == null) steps = new HashSet<>();
        if (string != null && !string.isBlank()) {
            string = string.toLowerCase();
            for (Step step : steps) {
                if (step.getDirections().toLowerCase().contains(string) ||
                    step.getIngredient().getName().toLowerCase().contains(string)
                ) {
                    AppLogger.info(Food.class, "Successfully filtered step by directions " + string);
                    matches.add(step);
                }
            }
        }
        return matches;
    }

    // getRecipes
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        AppLogger.debug(Food.class, "Getting recipes associated with the steps of this food. ");
        if (steps == null) steps = new HashSet<>();
        for (Step step : steps) {
            recipes.add(step.getRecipe());
        }
        AppLogger.info(Food.class, "Successfully retrieved recipes associated with the steps of this food. ");
        return recipes;
    }

    public Set<Meal> getMeals () {
        Set<Meal> meals = new HashSet<>();
        for (Recipe recipe : getRecipes()) {
            meals.addAll(recipe.getMealsAsSet());
        }
        return meals;
    }

    /* Update methods */
    public void getUpdate(Food source) {
        FoodValidator.validateUpdatePair(this, source);
        if (source == this) return;
        setName(source.getName());
        setSteps(source.getStepsAsSet());
    }

    /* Equals and hash methods */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (object instanceof Food food) {
            boolean equalIds = id != null && food.getId() != null && id.equals(food.getId());
            boolean equalNames = name != null && food.getName() != null && name.equalsIgnoreCase(food.getName());
            return equalIds && equalNames;
        }
        return false;
    }

    @Override
    public int hashCode() { return id != null ? Objects.hash(id) : Objects.hash(name); }

    /* String methods */
    @Override
    public String toString() { return getClass().getSimpleName() + "[id:" + id + " name:" + name + "]"; }
}
