package com.lawal.banji.springkitchen.recipe.repo;

import com.lawal.banji.springkitchen.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    Recipe findById(long id);
    Recipe findByTitle(String title);
    Recipe findByTitleIgnoreCase(String title);
    Recipe findByDescriptionIgnoreCase(String description);

    @Query("SELECT r FROM Recipe r WHERE lower(r.title) LIKE lower(:query) OR lower(r.description) LIKE lower(:query)")
    Set<Recipe> search(@Param("query") String query);
}
