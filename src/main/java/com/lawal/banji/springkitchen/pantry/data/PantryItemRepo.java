package com.lawal.banji.springkitchen.pantry.data;

import com.lawal.banji.springkitchen.pantry.model.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PantryItemRepo extends JpaRepository<PantryItem, Long> {
    PantryItem findByName(String name);
}
