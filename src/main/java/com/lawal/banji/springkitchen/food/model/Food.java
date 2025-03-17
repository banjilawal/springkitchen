package com.lawal.banji.springkitchen.food.model;

import com.lawal.banji.springkitchen.food.model.exception.FoodNameNullException;
import com.lawal.banji.springkitchen.common.AppLogger;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "foods")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Food {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = FoodNameNullException.MESSAGE)
    String name;

    /* Bidirectional fields */
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Step> steps = new HashSet<>();


    public Food(Long id, String name) { //}, Step step) {
        setId(id);
        setName(name);
        this.steps = new HashSet<>();
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public Set<Step> getStepsSet() { return steps; }

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
    public Set<Step> getSteps() {
        AppLogger.debug(Food.class, "Getting steps");
//        FoodValidator.validateFoodSteps(this);
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        AppLogger.debug(Food.class, "Setting steps");
        FoodValidator.validateFoodMethodStepSetParameter(steps);
        if (this.steps == null) this.steps = new HashSet<>();
        this.steps.clear();
        for (Step step : steps) { addStep(step); }
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

    /* Update methods */
    public void getUpdate(Food source) {
//        FoodValidator.validateUpdatePair(this, source);
        if (source == this) return;
        setName(source.getName());
        setSteps(source.getSteps());
    }

    /* String methods */
    public String stepsToString() {
        StringBuilder stringBuilder = new StringBuilder("Steps:");
        if (steps == null) {
            steps = new HashSet<>();
            return "steps:0";
        } else {
            int counter = 1;
            for (Step step : steps) {
                stringBuilder.append(counter)
                    .append(" stepId:")
                    .append(step.getId())
                    .append(" recipeId:")
                    .append(step.getRecipe())
                    .append("\n");
                counter++;
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        if (this == null) return "null food";
        return getClass().getSimpleName() + "[id:" + id + " name:" + name + " total steps:" + steps.size() + "]"; }
}