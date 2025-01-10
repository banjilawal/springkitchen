package com.lawal.banji.springkitchen.recipe.model;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    /* String constants */
    public static final String INGREDIENT_UPDATE_SOURCE_CANNOT_BE_NULL = "Ingredient update Source cannot be null";
    public static final String INVALID_UPDATE_SOURCE_ID = "Ingredient update source id is not valid. Update failed";

    private static final String METHOD_DOES_NOT_ACCEPT_NULL_ID_PARAMETERS = "Ingredient method does not allow null id parameters";
    public static final String NULL_STEPS_COLLECTION_NOT_ALLOWED = "Ingredient method doe not allow null step collections";
    public static final String NULL_STEP_CANNOT_BE_PASSED_TO_INGREDIENT_METHOD = "Ingredient method does not allow a null step";

    public static final String INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK = "Ingredient Name cannot be null or blank";
    public static final String INGREDIENT_STEPS_CANNOT_BE_NULL = "Cannot set steps to null";

    /* logger */
    private static final Logger logger = LoggerFactory.getLogger(Ingredient.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String name;

    /* Bidirectional fields */
    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Step> steps = new HashSet<>();

    /* Constructors */
    public Ingredient() {}

    public Ingredient(Long id, String name) { //}, Step step) {
        this.id = id;
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
    public void setId(Long id) { this.id = id; }

    public void setName (String name) {
        if (name == null || name.trim().isBlank()) loggingExceptionHandler(INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);
        else { this.name = name.trim(); }
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
        if (step == null) loggingExceptionHandler(NULL_STEP_CANNOT_BE_PASSED_TO_INGREDIENT_METHOD);
        else {
            if (this.steps == null) this.steps = new HashSet<>();
            if (!steps.contains(step)) {
                steps.add(step);
                step.setIngredient(this);
            }
        }
    }

    public void removeStep(Step step) {
        if (step == null) loggingExceptionHandler(NULL_STEP_CANNOT_BE_PASSED_TO_INGREDIENT_METHOD);
        else {
            if (this.steps == null) this.steps = new HashSet<>();
            if (steps.contains(step)) {
                this.steps.remove(step);
                if (step.getIngredient() == this) step.setRecipe(null);
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

    /* Update methods */
    public void getUpdate(Ingredient source) {
        if (source == this) return;

        if (source == null) loggingExceptionHandler(INGREDIENT_UPDATE_SOURCE_CANNOT_BE_NULL);
        else if (source.getSteps() == null) loggingExceptionHandler(INGREDIENT_STEPS_CANNOT_BE_NULL);
        else if (source.getId() == null || !id.equals(source.getId())) loggingExceptionHandler(INVALID_UPDATE_SOURCE_ID);
        else if (source.getName() == null || source.getName().trim().isEmpty()) loggingExceptionHandler(INGREDIENT_NAME_CANNOT_BE_NULL_OR_BLANK);
        else {
            setName(source.getName());
            setSteps(source.getStepsAsSet());
        }
    }

    /* Equals and hash methods */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (object instanceof Ingredient ingredient) {
            boolean equalIds = id != null && ingredient.getId() != null && id.equals(ingredient.getId());
            boolean equalNames = name != null && ingredient.getName() != null && name.equalsIgnoreCase(ingredient.getName());
            return equalIds && equalNames;
        }
        return false;
    }

    @Override
    public int hashCode() { return id != null ? Objects.hash(id) : Objects.hash(name); }

    /* String methods */
    @Override
    public String toString() { return getClass().getSimpleName() + "[id:" + id + " name:" + name + "]"; }

    /* logging methods */
    public void loggingExceptionHandler (String errorMessage) {
        logger.error(errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}
