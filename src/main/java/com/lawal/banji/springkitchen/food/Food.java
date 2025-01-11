package com.lawal.banji.springkitchen.food;

import com.lawal.banji.springkitchen.food.exception.*;
import com.lawal.banji.springkitchen.meal.Meal;
import com.lawal.banji.springkitchen.recipe.LogManager;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.recipe.model.Step;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Entity
@Table(name = "foods")
public class Food {

    /* String constants */
//    public static final String INGREDIENT_UPDATE_SOURCE_CANNOT_BE_NULL = "Food update Source cannot be null";
//    public static final String INVALID_UPDATE_SOURCE_ID = "Food update source id is not valid. Update failed";

//    public static final String METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS = "Food method does not allow null id parameters";
//    public static final String METHOD_DOES_NOT_ACCEPT_NULL_STRING_PARAMETERS = "Food method does not allow null string parameters";
//    public static final String NULL_STEPS_COLLECTION_NOT_ALLOWED = "Food method doe not allow null step collections";
//    public static final String NULL_STEP_CANNOT_BE_PASSED_TO_INGREDIENT_METHOD = "Food method does not allow a null step";

//    public static final String INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK = "Food Name cannot be null or blank";
//    public static final String INGREDIENT_STEPS_CANNOT_BE_NULL = "Cannot set steps to null";

    /* logger */
    private static final Logger logger = LoggerFactory.getLogger(Food.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
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
        FoodValidator.validateFoodMethodIdParameter(id);
        this.id = id;
    }

    public void setName (String name) {
//        if (name == null || name.trim().isBlank()) {
//            LogManager.logAndThrow(logger, FoodNameNullException.MESSAGE);
//            throw new FoodNameNullException();
//        }
//        else { this.name = name.trim(); }
        FoodValidator.validateFoodMethodStringParameter(name);
        this.name = name.trim();
    }

    // Step mutator methods
    public void setSteps(Set<Step> steps) {
//        if (steps == null) {
//            LogManager.logAndThrow(logger, SetFoodStepCollectionToNullException.MESSAGE);
//            throw new SetFoodStepCollectionToNullException();
//        }
//        else {
        FoodValidator.validateFoodMethodStepSetParameter(steps);
        if (this.steps == null) this.steps = new HashSet<>();
        this.steps.clear();
        steps.forEach(this::addStep);
//        }
    }

    public void addStep(Step step) {
//        if (step == null) {
//            LogManager.logAndThrow(logger, FoodMethodNullStepParameterException.MESSAGE);
//            throw new FoodMethodNullStepParameterException();
//        }
//        else {
        FoodValidator.validateFoodMethodStepParameter(step);
        if (this.steps == null) this.steps = new HashSet<>();
        if (!steps.contains(step)) {
            steps.add(step);
            step.setIngredient(this);
        }
//        }
    }

    public void removeStep(Step step) throws IllegalArgumentException {
//        if (step == null) {
//            LogManager.logAndThrow(logger, FoodMethodNullStepParameterException.MESSAGE);
//            throw new FoodMethodNullStepParameterException();
//        }
//        else {
        FoodValidator.validateFoodMethodStepParameter(step);
        if (this.steps == null) this.steps = new HashSet<>();
        if (steps.contains(step)) {
            this.steps.remove(step);
            if (step.getIngredient().equals(this)) step.setRecipe(null);
        }
    }

    /* Search methods */
    // find a unique step
    public Step findStepById(Long id) {
//        if (id == null) { LogManager.logAndThrow(logger, FoodMethodNullIdParameterException.MESSAGE);
//            throw new FoodMethodNullIdParameterException();
//        }
        FoodValidator.validateFoodMethodIdParameter(id);
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
                if (step.getDirections().toLowerCase().contains(string) ||
                    step.getIngredient().getName().toLowerCase().contains(string)
                ) matches.add(step);
            }
        }
        return matches;
    }

    // getRecipes
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        if (steps == null) steps = new HashSet<>();
        for (Step step : steps) {
            recipes.add(step.getRecipe());
        }
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
        FoodValidator.validateFoodUpdateSource(source);
        if (source == this) return;

//        validate(source == null, NullFoodUpdateSourceException.MESSAGE);
//        validate(source.getSteps() == null, FoodMethodNullStepCollectionParameterException.MESSAGE);
//        validate(source.getId() == null, FoodUpdateSourceNullIdException.MESSAGE );
//        validate(source.getName() == null || source.getName().trim().isBlank(), FoodNameNullException.MESSAGE);
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
