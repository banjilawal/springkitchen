package com.lawal.banji.springkitchen.recipe.model;

import com.lawal.banji.springkitchen.recipe.dto.StepDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public final class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String directions;

    @Column(nullable = true)
    private Long minutesDuration;

    @ManyToMany(mappedBy = "steps")
    Set<Recipe> recipes;

    public Step() { this(null, null, null);}

    public Step(Builder builder) {
        this(builder.id, builder.directions, builder.minutesDuration);
    }

    public Step(Long id, String directions, Long minutesDuration) {
        this.id = id;
        this.directions = directions;
        this.minutesDuration = minutesDuration;
        this.recipes = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getDirections() {
        return directions;
    }

    public Long getMinutesDuration() {
        return minutesDuration;
    }

    public Set<Recipe> getRecipes () {
        return recipes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDirections(String instruction) {
        this.directions = instruction;
    }

    public void setMinutesDuration(Long minutes) {
        this.minutesDuration = minutes;
    }

    public void setRecipes(Set<Recipe> otherRecipes) {
        clearRecipes();
        for (Recipe recipe : otherRecipes) {
            if (findRecipeById(recipe.getId()) == null) this.recipes.add(recipe);
        }
    }

    public void clearRecipes() { this.recipes = new HashSet<>(); }

    public Recipe findRecipeTitle (String title) {
        for (Recipe recipe : this.recipes) {
            if (recipe.getTitle().equalsIgnoreCase(title)) {
                return recipe;
            }
        }
        return null;
    }

    public void addRecipe(Recipe recipe) {
        if (findRecipeById(recipe.getId()) == null) this.recipes.add(recipe);
        recipe.getSteps().add(this);
    }

    public void removeRecipe(Recipe recipe) {
        if (findRecipeById(recipe.getId()) != null) this.recipes.remove(recipe);
        recipe.getSteps().remove(this);
    }

    public Recipe findRecipeById (Long id) {
        for (Recipe recipe : this.recipes) {
            if (recipe.getId().equals(id)) { return recipe; }
        }
        return null;
    }

    public Set<Recipe> searchRecipes (String string) {
        Set<Recipe> recipes = new HashSet<>();
        string = string.toLowerCase();
        for (Recipe recipe : this.recipes) {
            if (recipe.getTitle().toLowerCase().contains(string) ||
                recipe.getDescription().toLowerCase().contains(string)
            ) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public StepDTO toDTO() {
        return new StepDTO(this.id, this.directions, this.minutesDuration);
    }

    public void update (StepDTO stepDTO) {
        this.id = stepDTO.getId();
        this.directions = stepDTO.getDirections();
        this.minutesDuration = stepDTO.getMinutesDuration();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (object instanceof Step step)
            return id.equals(step.getId())
                && directions.equalsIgnoreCase(step.getDirections())
                && minutesDuration.equals(step.getMinutesDuration());
        return false;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString () {
        return "Step Detail[id:" +  id + " minutes:" + minutesDuration.toString()  + " directions:" + directions + "]";
    }

    public String recipesToString() {
        StringBuilder stringBuilder = new StringBuilder("Recipes:\n");
        int counter = 1;
        for (Recipe recipe: this.recipes) {
            stringBuilder.append(counter).append(" ").append(recipe.toString()).append("\n");
            counter++;
        }
        return stringBuilder.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String directions;
        private Long minutesDuration;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder directions(String directions) {
            this.directions = directions;
            return this;
        }

        private Builder minutesDuration(Long minutesDuration) {
            this.minutesDuration = minutesDuration;
            return this;
        }

        public Step build () {
            return new Step(this);
        }
    }
}
