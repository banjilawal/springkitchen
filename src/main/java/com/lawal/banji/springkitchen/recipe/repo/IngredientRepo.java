package com.lawal.banji.springkitchen.recipe.repo;

import com.lawal.banji.springkitchen.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
    Ingredient findById(long id);
    Ingredient findByNameIgnoreCase(String name);
    Set<Ingredient> findByNameContainingIgnoreCase(String keyword);
}
