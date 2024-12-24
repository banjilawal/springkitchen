package com.lawal.banji.springkitchen.recipe.data;

import com.lawal.banji.springkitchen.recipe.dto.StepDTO;
import com.lawal.banji.springkitchen.recipe.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepo extends JpaRepository<Step, Long> {
}
