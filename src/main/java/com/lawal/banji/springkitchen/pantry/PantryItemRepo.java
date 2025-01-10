package com.lawal.banji.springkitchen.pantry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PantryItemRepo extends JpaRepository<PantryItem, Long> {
    PantryItem findById(long id);
    PantryItem findByNameIgnoreCase(String name);
    Set<PantryItem> findByNameContainingIgnoreCase(String string);
}
