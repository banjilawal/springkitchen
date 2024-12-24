package com.lawal.banji.springkitchen.recipe.model;

import com.lawal.banji.springkitchen.recipe.dto.RecipeDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public final class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    @JoinTable(name = "recipe_step",
        joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "step_id", referencedColumnName = "id")
    )
    private Set<Step> steps;

    public Recipe() {}

    public Recipe(Builder builder) {
        this(builder.id, builder.title, builder.description);
    }

    public Recipe(String title, String description) {
        this(null, title, description);
    }

    public Recipe(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.steps = new HashSet<>();
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

    public Set<Step> getSteps() {
        return steps;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSteps(Set<Step> otherSteps) {
        clearSteps();
        for (Step otherStep : otherSteps) {
            if (findStepById(otherStep.getId()) == null) this.steps.add(otherStep);
        }
    }

    public void clearSteps() { this.steps= new HashSet<>(); }

    public void addRStep(Step step) {
        if (findStepById(step.getId()) == null) this.steps.add(step);
        step.getRecipes().add(this);
    }

    public void removeStep(Step step) {
        if (findStepById(step.getId()) != null) this.steps.remove(step);
        step.getRecipes().remove(this);
    }

    public Step findStepById (Long id) {
        for (Step step : this.steps) {
            if (step.getId().equals(id)) {
                return step;
            }
        }
        return null;
    }

    public Set<Step> searchStepDirections (String string) {
        Set<Step> steps = new HashSet<>();
        string = string.toLowerCase();
        for (Step step : this.steps) {
            if (step.getDirections().toLowerCase().contains(string)) {
                steps.add(step);
            }
        }
        return steps;
    }

    public Step findStepDescription(String description) {
        for (Step step : this.steps) {
            if (step.getDirections().equalsIgnoreCase(description)) {
                return step;
            }
        }
        return null;
    }

    public RecipeDTO toDTO() {
        return new RecipeDTO(this.id, this.title, this.description);
    }

    public void update (RecipeDTO recipeDTO) {
        this.id = recipeDTO.getId();
        this.title = recipeDTO.getTitle();
        this.description = recipeDTO.getDescription();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (object instanceof Recipe recipe)
            return id.equals(recipe.getId()) && title.equalsIgnoreCase(recipe.getTitle());
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    @Override
    public String toString() {
        return "Recipe[id:" + id  + " title:" + title + " description:" + description + "]";
    }

    public String stepsToString() {
        StringBuilder stringBuilder = new StringBuilder("Steps:\n");
        int counter = 1;
        for (Step step : steps) {
            stringBuilder.append(counter).append(" ").append(step.getDirections()).append("\n");
            counter++;
        }
        return stringBuilder.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

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

        public Recipe build () {
            return new Recipe(this);
        }
    }
}
