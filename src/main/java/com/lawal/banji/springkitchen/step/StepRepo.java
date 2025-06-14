package com.lawal.banji.springkitchen.step;

import com.lawal.banji.springkitchen.recipe.model.Recipe;
import com.lawal.banji.springkitchen.step.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StepRepo extends JpaRepository<Step, Long> {

    void deleteAllByRecipe(Recipe recipe);

    @Query("SELECT s FROM Step s WHERE LOWER(s.directions) LIKE LOWER(CONCAT('%', :string, '%'))")
    Set<Step> findByDirectionsContainingIgnoreCase(@Param("string") String string);

    Set<Step> searchByDirectionsContainingIgnoreCase(String string);
}


