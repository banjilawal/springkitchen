package com.lawal.banji.springkitchen.recipe.data;

import com.lawal.banji.springkitchen.recipe.dto.RecipeDTO;
import com.lawal.banji.springkitchen.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    Recipe findByTitle(String title);
}
