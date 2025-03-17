package com.lawal.banji.springkitchen.meal.model;

import com.lawal.banji.springkitchen.meal.model.exception.NullMealFunctionParameterException;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "meals")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Meal {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    LocalDateTime servedAt;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Meal(Long id, LocalDateTime servedAt, Recipe recipe) {
        this.id = id;
        this.servedAt = servedAt;

        if (recipe != null) recipe.addMeal(this);
        this.recipe = recipe;
    }


    public void setRecipe(Recipe recipe) {
        if (recipe == this.recipe) return;

        if (this.recipe != null) this.recipe.removeMeal(this);
        this.recipe = recipe;

        if (this.recipe != null) this.recipe.addMeal(this);
    }

    public void getUpdate(Meal source) {
        if (source == this) throw new NullMealFunctionParameterException();
        setRecipe(source.getRecipe());
        setServedAt(source.getServedAt());
    }

    public boolean isInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return servedAt.isAfter(startDate) && servedAt.isBefore(endDate);
    }

    @Override
    public String toString() {
        if (this == null) return "null meal object";
        String recipeTitle = recipe == null ? "null" : recipe.getTitle();
        return getClass().getSimpleName() + "[id:" + id + " recipe:" + recipe.getTitle() + " servedAt:" + servedAt + "]";
    }
}