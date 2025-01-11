package com.lawal.banji.springkitchen.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {
    Food findById(long id);
    Food findByNameIgnoreCase(String name);
    Set<Food> findByNameContainingIgnoreCase(String keyword);
}
