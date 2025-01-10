package com.lawal.banji.springkitchen.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepo extends JpaRepository<Meal, Long> {
}
