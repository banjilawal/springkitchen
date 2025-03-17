package com.lawal.banji.springkitchen.step.model;

import com.lawal.banji.springkitchen.food.model.Food;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "steps")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Step {

    /* String constants */
    public static final String STEP_UPDATE_SOURCE_CANNOT_BE_NULL = "Update source is null. Update failed";
    public static final String INVALID_UPDATE_SOURCE_ID = "Update source id is invalid. Update failed";
    public static final String STEP_INGREDIENT_AMOUNT_CANNOT_BE_NEGATIVE = "Food cannot be null or blank";
    public static final String STEP_DIRECTIONS_CANNOT_BE_NULL_OR_BLANK = "Directions cannot be null or blank";
    public static final String STEP_DURATION_CANNOT_BE_NEGATIVE = "Duration minutes cannot be negative";

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = STEP_DIRECTIONS_CANNOT_BE_NULL_OR_BLANK)
    private String instruction;

    /* Bidirectional fields */
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(optional = true)
    @JoinColumn(name = "ingredient_id")
    private Food ingredient;


    public Step(Long id, Recipe recipe, Food ingredient, String instruction) {
        this.id = id;
        this.recipe = recipe;
        if (recipe != null) {
            recipe.getSteps().add(this);
//            System.out.println("Step constructor recipe: " + recipe.toString());
        }

        this.ingredient = ingredient;
        if (ingredient != null) ingredient.addStep(this);
        setInstruction(instruction);
    }

    public void setRecipe(Recipe recipe) {
        if (recipe != null && recipe.equals(this.recipe)) return;

        if (this.recipe != null) {
            this.recipe.removeStep(this);
            this.recipe = null;
        }

        if (recipe != null) {
            this.recipe = recipe;
            this.recipe.addStep(this);
        }
    }

    public void setIngredient(Food ingredient) {
        if (ingredient != null && ingredient.equals(this.ingredient) ) return;

        if (this.ingredient != null) {
            this.ingredient.removeStep(this);
            this.ingredient = null;
        }

        if (ingredient != null) {
            this.ingredient = ingredient;
            this.ingredient.getSteps().add(this);
            System.out.println("Step.setIngredient: " + ingredient.toString() + ingredient.getSteps().size());
        }
    }

    public void setInstruction (String directions) {
        StepValidator.validateStepMethodStringParameter(directions);
        this.instruction = directions.trim();
    }


    /* Update methods */
    public void getUpdate(Step source) {

        if (source == this) return;
        StepValidator.validateStepUpdateSource(source);
        StepValidator.validateIdMatch(this, source);
        setInstruction(source.getInstruction());

        setRecipe(source.getRecipe());
        setIngredient(source.getIngredient());
    }

    /* String methods */
    @Override
    public String toString () {
        if (this == null) return "null step object";
        String ingredientString = ingredient == null ? "" : ingredient.getName();
        return getClass().getSimpleName()
            + "[id:" + id
            + ingredientString
            + " directions:" + instruction +"]";
    }
}