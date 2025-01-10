package com.lawal.banji.springkitchen.meal;

import com.lawal.banji.springkitchen.recipe.model.Recipe;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Column(nullable = false)
    LocalDateTime mealTime;

    public Meal() {}

    public Meal(Long id, Recipe recipe) {
        this.id = id;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public LocalDateTime getMealTime() {
        return mealTime;
    }

    public void setId(Long id) {
        if (id == null) return;
        this.id = id;
    }

    public void setRecipe(Recipe recipe) {
        if (recipe == this.recipe) return;
        this.recipe.removeMeal(this);
        this.recipe = recipe;
        if (recipe != null) {
            recipe.addMeal(this);
        }
    }

    public void setMealTime(LocalDateTime mealTime) {
        this.mealTime = mealTime;
    }

    public void getUpdate(Meal source) {}

    public boolean isInDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return mealTime.isAfter(startDate) && mealTime.isBefore(endDate);
    }
}
