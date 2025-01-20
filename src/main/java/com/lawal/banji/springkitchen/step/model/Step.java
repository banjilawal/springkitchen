package com.lawal.banji.springkitchen.step.model;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Entity
@Table(name = "steps")
public class Step {

    /* String constants */
    public static final String STEP_UPDATE_SOURCE_CANNOT_BE_NULL = "Update source is null. Update failed";
    public static final String INVALID_UPDATE_SOURCE_ID = "Update source id is invalid. Update failed";
    public static final String STEP_INGREDIENT_AMOUNT_CANNOT_BE_NEGATIVE = "Food cannot be null or blank";
    public static final String STEP_DIRECTIONS_CANNOT_BE_NULL_OR_BLANK = "Directions cannot be null or blank";
    public static final String STEP_DURATION_CANNOT_BE_NEGATIVE = "Duration minutes cannot be negative";


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = STEP_DIRECTIONS_CANNOT_BE_NULL_OR_BLANK)
    private String directions;


    @Column(nullable = true)
    private Long durationMinutes;

    @Column(nullable = true)
    private Double ingredientAmount;

    /* Bidirectional fields */
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(optional = true)
    @JoinColumn(name = "ingredient_id")
    private Food ingredient;

    /* Constructors */
    public Step() {}

    public Step(
        Long id,
        Recipe recipe,
        Food ingredient,
        Double ingredientAmount,
        String directions,
        Long durationMinutes
    ) {
        this.id = id;
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.ingredientAmount = ingredientAmount;

        setDirections(directions);
        setDurationMinutes(durationMinutes);
    }

    /* Builders */
    public static Builder builder () { return new Builder(); }

    public static class Builder {
        private Long id;
        private Recipe recipe;
        private Food ingredient;
        private Double ingredientAmount;
        private String directions;
        private Long durationMinutes;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setRecipe(Recipe recipe) {
            this.recipe = recipe;
            return this;
        }

        public Builder setIngredient(Food food) {
            this.ingredient = food;
            return this;
        }

        public Builder setIngredientAmount(Double ingredientAmount) {
            this.ingredientAmount = ingredientAmount;
            return this;
        }

        public Builder setDirections(String directions) {
            this.directions = directions;
            return this;
        }

        public Builder setDurationMinutes(Long durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        public Step build() {
            return new Step(id, recipe, ingredient, ingredientAmount, directions, durationMinutes);
        }
    }

    /* Getters */
    public Long getId() {
        return id;
    }

    public Recipe getRecipe() { return recipe; }

    public Food getIngredient() { return ingredient; }

    public Double getIngredientAmount() { return ingredientAmount; }

    public String getDirections() { return directions; }

    public Long getDurationMinutes() {
        return durationMinutes;
    }

    /* Setters */
    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipe(Recipe recipe) {
        if (recipe == this.recipe) return;
        if (recipe != null) recipe.addStep(this);
        if (this.recipe != null) this.recipe.removeStep(this);
        this.recipe = recipe;
    }

    public void setIngredient(Food food) {
        if (food == this.ingredient) return;
        if (food != null) food.addStep(this);
        if (this.ingredient != null) this.ingredient.removeStep(this);
        this.ingredient = food;
    }

    public void setIngredientAmount(Double ingredientAmount) {
        StepValidator.validateStepIngredientAmount(ingredientAmount);
        this.ingredientAmount = ingredientAmount;
    }

    public void setDirections(String directions) {
        StepValidator.validateStepMethodStringParameter(directions);
        this.directions = directions.trim();
    }

    public void setDurationMinutes(Long minutesDuration) {
        StepValidator.validateStepMinutes(minutesDuration);
        this.durationMinutes = minutesDuration;
    }

    /* Update methods */
    public void getUpdate(Step source) {

        if (source == this) return;
        StepValidator.validateStepUpdateSource(source);
        StepValidator.validateIdMatch(this, source);
        setDirections(source.getDirections());
        setDurationMinutes(source.getDurationMinutes());

        setRecipe(source.getRecipe());
        setIngredient(source.getIngredient());
        setIngredientAmount(source.getIngredientAmount());
    }

    /* Equals and hash methods */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (object instanceof Step step) {
            boolean idEqual = id != null && step.getId() != null && id.equals(step.getId());
            boolean directionsEqual = directions != null && step.getDirections() != null
                &directions.equalsIgnoreCase(step.directions);
            return idEqual && directionsEqual;
        }
        return false;
    }

    @Override
    public int hashCode() { return id != null ? Objects.hash(id) : Objects.hash(directions); }

    /* String methods */
    @Override
    public String toString () {
        String durationString = durationMinutes == null ? "minutes:" : durationMinutes + "";
        String ingredientString = ingredient == null ? "" : ingredient.getName() + " amount" + ingredientAmount + " ";
        String ingredientAmountString = ingredientAmount == null ? "" : "amount:" + ingredientAmount;
        return getClass().getSimpleName()
            + "[id:" + id
            + ingredientString
            + " directions:" + directions + durationString + "]";
    }

    /* Nicely combines the food, it's amount and the expected duration into a single string  */
//    public String directionStringHandler () {
//        String ingredientString = ingredient == null ? "" : ingredient.getName();
//        String ingredientAmountString = ingredientAmount == null ? "" : "amount:" + ingredientAmount;
//
//        int firstWhiteSpaceLocation = directions.indexOf(" ");
//        if (ingredient == null || ingredientAmount == null || firstWhiteSpaceLocation == -1) return directions;
//        return directions.substring(0, firstWhiteSpaceLocation + 1) + ingredientAmountString + " " + ingredientString +
//            directions.substring(firstWhiteSpaceLocation + 1);
//    }
}
